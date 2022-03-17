package com.example.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 主流程
 */
@Data
@TableName(value = "flow_main")
public class FlowMain {

    private Long id;

    private Long mainId;

    private String roleType;

    private String userGroup;

    private Long passStep;

    private Long refuseStep;

    private Integer status;

    private Integer rank;

}
