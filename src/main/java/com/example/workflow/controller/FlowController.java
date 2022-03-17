package com.example.workflow.controller;

import com.example.workflow.domain.Business;
import com.example.workflow.domain.FlowMain;
import com.example.workflow.domain.vo.ApproveParam;
import com.example.workflow.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/flow")
public class FlowController {

    @Autowired
    FlowService flowService;

    @GetMapping("list")
    @ResponseBody
    public List<FlowMain> list(@RequestParam Long id){
        return flowService.list(id,null);
    }

    /**
     * 通过审核，id为业务id
     * @param param
     */
    @GetMapping("pass")
    @ResponseBody
    public void pass(@RequestParam ApproveParam param){
        flowService.pass(param, Business.class);
    }

    /**
     * 驳回审核，id为业务id
     * @param param
     */
    @GetMapping("refuse")
    @ResponseBody
    public void refuse(@RequestParam ApproveParam param){
        flowService.refuse(param,Business.class);
    }

}
