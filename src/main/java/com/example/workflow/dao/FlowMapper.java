package com.example.workflow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.workflow.domain.FlowMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FlowMapper extends BaseMapper<FlowMain> {

    @Select("select * from flow_main where main_id in (select flow_id from flow_business_relation where business = #{tableName}) order by rank")
    List<FlowMain> getFlowByBusiness(String tableName);

    /**
     * 动态更新对应业务主表的approve_status字段，没有拉倒
     * @param id
     * @param tableName
     * @param status
     * @return
     */
    @Update("update ${tableName} set approve_status = #{status} where id = #{id}")
    int updateBusinessById(String id, String tableName, Integer status);
}
