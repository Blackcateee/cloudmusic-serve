package com.cloudmusic.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("t_actions")
public class UserAction {
    //id
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long actionId;
    //用户id
    private Long actionUser;
    //歌曲id
    private Long actionSong;
    //喜欢
    private int actionLike;
    //不喜欢
    private int actionUnLike;
    //试听
    private int actionAudition;
    //下载
    private int actionDownload;
}
