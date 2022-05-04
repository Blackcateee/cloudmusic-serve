package com.cloudmusic.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_admin")
public class Admin {

    //id
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    //账号
    private String account;
    //密码
    private String password;
    //权限
    private int access;
    //操作时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date insertTime;
}
