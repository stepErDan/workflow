package com.example.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 主流程信息
 */
@Data
@TableName(value = "flow_info")
public class FlowInfo {

    private Long id;

    private String name;

    private Integer isDelete;

}
