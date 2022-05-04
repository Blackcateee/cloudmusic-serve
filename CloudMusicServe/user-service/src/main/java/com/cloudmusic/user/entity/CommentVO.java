package com.cloudmusic.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {
    //用户图片
    private String userImg;
    //评论内容
    private String comment;
    //用户昵称
    private String userNickName;
    //评论时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date commentTime;
}
