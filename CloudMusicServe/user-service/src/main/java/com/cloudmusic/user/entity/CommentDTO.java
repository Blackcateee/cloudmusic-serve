package com.cloudmusic.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CommentDTO {
    //用户name
    private String userName;
    //歌单id
    private Long songSheetId;
    //歌曲id
    private Long songId;
    //评论内容
    private String comment;
}
