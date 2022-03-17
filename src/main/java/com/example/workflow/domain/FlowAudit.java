package com.example.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审核表
 */
@Data
@TableName(value = "flow_audit")
public class FlowAudit {

    @TableId(type = IdType.AUTO)    //自增主键
    private Long id;

    //流程id，对应流程的main_id
    private Long flowId;

    //当前步骤id，对应流程id
    private Long stepId;

    private LocalDateTime submitTime;

    private LocalDateTime approveTime;

    private String info;

    private String submitUser;

    private String approveUser;

    private String businessId;

    //指定下一个审核用户
    private String nextUserId;

}
