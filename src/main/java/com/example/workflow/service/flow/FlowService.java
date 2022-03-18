package com.example.workflow.service.flow;

import com.example.workflow.domain.FlowAudit;
import com.example.workflow.domain.FlowMain;
import com.example.workflow.domain.vo.ApproveParam;
import com.sun.istack.internal.NotNull;

import java.lang.annotation.Annotation;
import java.util.List;

public interface FlowService {

    List<FlowMain> list(@NotNull Long id);

    List<FlowMain> list(@NotNull Long id,@NotNull Class clazz);

    <T> void pass(@NotNull ApproveParam param, @NotNull Class<T> clazz);

    <T> void refuse(@NotNull ApproveParam param,@NotNull Class<T> clazz);

    /**
     * 初始化第一条工作流
     *
     * @return
     */
    FlowAudit initFlow(@NotNull FlowAudit audit,@NotNull Class clazz);
}
