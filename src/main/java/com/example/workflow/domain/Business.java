package com.example.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 业务表
 */
@Data
@TableName(value = "business")
public class Business {

    private Long id;

    private String name;

}
