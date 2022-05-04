package com.cloudmusic.song.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_singer")
public class Singer {

    //歌手id
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long singerId;
    //歌手链接
    private String singerLink;
    //歌手名字
    private String singerName;
    //歌手别名
    private String singerAlias;
    //歌手图片
    private String singerImg;
    //插入时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date insertTime;
}
