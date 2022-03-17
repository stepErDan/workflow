package com.example.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
//@ApiModel(value = "用户表", description = "用户表")
public class SysUser implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
//    @ApiModelProperty("主键id")
    private Long id;

//    @ApiModelProperty("账号")
    private String account;

//    @ApiModelProperty("密码")
    private String password;

//    @ApiModelProperty("名称")
    private String nickname;

//    @ApiModelProperty("手机号")
    private String mobile;

//    @ApiModelProperty("e-mail")
    private String email;

//    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

//    @ApiModelProperty("删除标志")
    @TableLogic
    private boolean is_deleted;

//    @ApiModelProperty("状态")
    private Integer status;
}
