package com.example.workflow.service.impl;

import com.example.workflow.builder.FlowAduitBuilder;
import com.example.workflow.dao.BusinessMapper;
import com.example.workflow.domain.Business;
import com.example.workflow.domain.FlowAudit;
import com.example.workflow.domain.SysUser;
import com.example.workflow.domain.vo.ApproveParam;
import com.example.workflow.service.BusinessService;
import com.example.workflow.service.FlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    final BusinessMapper businessMapper;

    final FlowService flowService;

    @Override
    public void add(Business business, SysUser sysUser) {
        int i = businessMapper.insert(business);
        if (i > 0){
            ApproveParam param = new ApproveParam(String.valueOf(business.getId()),String.valueOf(sysUser.getId()));
            flowService.pass(param,Business.class);
            FlowAudit flowAudit = FlowAduitBuilder
                    .createFlowAudit(business.getId(),sysUser.getId())
                    .build();
            flowService.initFlow(flowAudit,Business.class);
        }
    }
}
