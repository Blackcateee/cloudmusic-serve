package com.cloudmusic.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_comment")
public class Comment {
    //id
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    //用户name
    private String userName;
    //歌单id
    @TableField("songSheet_id")
    private Long songSheetId;
    //歌曲id
    private Long songId;
    //评论内容
    private String comment;
    //添加时间
    @JsonFormat(pattern = "yyyy-MM-hh")
    private Date insertTime;
}
