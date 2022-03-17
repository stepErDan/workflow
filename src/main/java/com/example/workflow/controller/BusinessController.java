package com.example.workflow.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.workflow.domain.Business;
import com.example.workflow.domain.SysUser;
import com.example.workflow.service.BusinessService;
import com.example.workflow.service.FlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/business")
public class BusinessController {

    private final BusinessService businessService;

    @GetMapping("add")
    @ResponseBody
    public void add(@RequestParam Long id){
        Business business = new Business();
        business.setId(IdWorker.getId());
        business.setName("flow");
        businessService.add(business,new SysUser());
    }

}
