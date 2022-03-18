package com.example.workflow.service.business.impl;

import com.example.workflow.builder.FlowAduitBuilder;
import com.example.workflow.dao.business.BusinessMapper;
import com.example.workflow.domain.Business;
import com.example.workflow.domain.FlowAudit;
import com.example.workflow.domain.SysUser;
import com.example.workflow.service.business.BusinessService;
import com.example.workflow.service.flow.FlowService;
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
//            ApproveParam param = new ApproveParam(String.valueOf(business.getId()),String.valueOf(sysUser.getId()));
//            flowService.pass(param,Business.class);
            FlowAudit flowAudit = FlowAduitBuilder
                    .createFlowAudit(String.valueOf(business.getId()),String.valueOf(sysUser.getId()))
                    .build();
            flowService.initFlow(flowAudit,Business.class);
        }
    }
}
