package com.example.workflow.domain.vo;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 审核入参Vo
 */
@Data
public class ApproveParam implements Serializable {

    //业务id主键
    @NotNull
    private String id;

    //当前操作用户id
    @NotNull
    private String currentUserId;

    //审核意见
    private String info;

    //指定审核人id
    private String approveUserId;

    public ApproveParam(String id,String currentUserId){
        this.id = id;
        this.currentUserId = currentUserId;
    }

}
