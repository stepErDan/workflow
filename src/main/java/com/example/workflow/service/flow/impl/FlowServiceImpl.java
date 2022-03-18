package com.example.workflow.service.flow.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.workflow.builder.FlowAduitBuilder;
import com.example.workflow.dao.flow.FlowAuditMapper;
import com.example.workflow.dao.flow.FlowMainMapper;
import com.example.workflow.domain.FlowAudit;
import com.example.workflow.domain.FlowMain;
import com.example.workflow.domain.vo.ApproveParam;
import com.example.workflow.service.flow.FlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FlowServiceImpl implements FlowService {

    private final FlowMainMapper flowMainMapper;

    private final FlowAuditMapper flowAuditMapper;

    @Override
    public List<FlowMain> list(Long id) {
        return flowMainMapper.selectList(new LambdaQueryWrapper<FlowMain>().eq(FlowMain::getMainId,id).orderByAsc(FlowMain::getRank));
    }

    @Override
    public List<FlowMain> list(Long id,Class clazz) {
        return flowMainMapper.selectList(new LambdaQueryWrapper<FlowMain>().eq(FlowMain::getMainId,id).orderByAsc(FlowMain::getRank));
    }

    @Override
    @Transactional
    public <T> void pass(ApproveParam param, Class<T> clazz) {
        FlowMain main = updateFlow(param,clazz);

        //4、判断流程是否完成
        if (!main.getPassStep().equals(-1l)){
            //未完结的流程
            //往下新增
            FlowAudit audit = FlowAduitBuilder.createFlowAudit(param.getId(),param.getCurrentUserId()).setApproveUser(param.getApproveUserId()).setFlowInfio(main).build();
            flowAuditMapper.insert(audit);
        }
    }

    @Override
    @Transactional
    public void refuse(ApproveParam param, Class clazz) {
        FlowMain main = updateFlow(param,clazz);

        //4、生成驳回信息
        FlowAudit audit = FlowAduitBuilder.createFlowAudit(param.getId(),param.getCurrentUserId())
                .setApproveUser(param.getApproveUserId())
                .setFlowId(main.getMainId())
                .setStepId(main.getRank() == 1?main.getId():main.getRefuseStep())
                .build();
        flowAuditMapper.insert(audit);
    }

    /**
     * 更新流程
     * @param param
     * @param clazz
     * @return
     */
    private FlowMain updateFlow(ApproveParam param, Class clazz){
        String tableName = getTableNameByTableNameAnnotation(clazz);
        //1、获取最新的审核步骤
        //获取主流程列表
        List<FlowMain> mainList = flowMainMapper.getFlowByBusiness(tableName);
        if (Objects.isNull(mainList) || mainList.size() == 0)
            throw new NullPointerException("未获取到业务对应流程，请检查表名是否正确或者关联表是否正确关联！");

        //根据主表流程获取最新一条的审核记录
        FlowAudit last = flowAuditMapper.getLastestAudit(mainList.get(0).getMainId(),param.getId());
        if (Objects.isNull(last)){
            throw new RuntimeException("此条业务未进行初始化，无法获取到最新审核数据！");
        }else if (Objects.nonNull(last.getApproveTime()))
            throw new RuntimeException("此条已被审核或已完结！不可重复审核！");

        //2、确定对应的主流程是哪个
        FlowMain main = getLastStepMain(last,mainList);

        //3、处理当前这条审核信息
        last.setApproveTime(LocalDateTime.now());
        last.setInfo(param.getInfo());
        last.setApproveUser(param.getCurrentUserId());
        flowAuditMapper.updateById(last);

        //5、更新业务
        flowMainMapper.updateBusinessById(param.getId(),tableName,main.getStatus());
        return main;
    }

    /**
     * 获取最后一条审核信息对应的主流程
     * @param last
     * @param mainList
     * @return
     */
    private FlowMain getLastStepMain(FlowAudit last, List<FlowMain> mainList){
        for(FlowMain flowMain:mainList){
            if (last.getStepId().equals(flowMain.getId())) {
                return flowMain;
            }
        }

        throw new RuntimeException("对应的主流程不存在！");
    }

    @Override
    public FlowAudit initFlow(FlowAudit audit, Class clazz) {
        //无绑定流程，初始化
        if (audit.getFlowId() == null){
            FlowMain first = getFirstFlowByClazz(clazz);
            if (first == null)
                throw new NullPointerException("业务对应的流程为null！");
            audit.setStepId(first.getId());
            audit.setFlowId(first.getMainId());
        }
        flowAuditMapper.insert(audit);
        return audit;
    }

    /**
     * 获取当前业务的第一个流程
     */
    private FlowMain getFirstFlowByClazz(Class clazz) {
        return flowAuditMapper.getFirstFlowByClazz(getTableNameByTableNameAnnotation(clazz));
    }

    /**
     * 通过Lombok的TableName注解获取表名
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> String getTableNameByTableNameAnnotation(Class<T> clazz){
        if (Objects.isNull(clazz.getAnnotation(TableName.class)))
            throw new RuntimeException("注解为空，获取不到业务表名！");

        return clazz.getAnnotation(TableName.class).value();
    }
}
