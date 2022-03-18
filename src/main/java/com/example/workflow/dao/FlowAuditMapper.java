package com.example.workflow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.workflow.domain.FlowAudit;
import com.example.workflow.domain.FlowMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FlowAuditMapper extends BaseMapper<FlowAudit> {

    @Select("select * from flow_audit where business_id = #{id} and main_id = #{mainId}")
    FlowAudit getLastestAudit(@Param("list") Long mainId, @Param("id") String id);

    /**
     * 获取业务对应的第一条流程信息
     * @param tableName
     * @return
     */
    @Select("select * from flow_main where main_id in (select flow_id from flow_business_relation where business = #{tableName}) and step = 1")
    FlowMain getFirstFlowByClazz(String tableName);

}
