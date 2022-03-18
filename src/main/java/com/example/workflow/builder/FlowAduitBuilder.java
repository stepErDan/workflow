package com.example.workflow.builder;

import com.example.workflow.domain.FlowAudit;
import com.example.workflow.domain.FlowMain;
import com.sun.istack.internal.NotNull;

import java.time.LocalDateTime;

/**
 *  静态工厂构建FlowAudit
 */
public class FlowAduitBuilder {

    private FlowAudit flowAudit;

    //开始
    public static FlowAduitBuilder createFlowAudit(@NotNull String businessId,@NotNull String submitUser){
        FlowAduitBuilder builder = new FlowAduitBuilder();
        builder.flowAudit = new FlowAudit();
        builder.flowAudit.setBusinessId(businessId);
        builder.flowAudit.setSubmitUser(submitUser);
        return builder;
    }

    //结尾
    public FlowAudit build(){
        if (flowAudit.getBusinessId() != null)
            throw new NullPointerException("businessId不可为空！");
        flowAudit.setSubmitTime(LocalDateTime.now());
        return flowAudit;
    }

    public FlowAduitBuilder setApproveUser(String id){
        flowAudit.setApproveUser(id);
        return this;
    }

    public FlowAduitBuilder setFlowInfio(FlowMain main){
        flowAudit.setFlowId(main.getMainId());
        flowAudit.setStepId(main.getId());
        return this;
    }

    public FlowAduitBuilder setStepId(Long stepId){
        flowAudit.setStepId(stepId);
        return this;
    }

    public FlowAduitBuilder setFlowId(Long flowId){
        flowAudit.setFlowId(flowId);
        return this;
    }
}
