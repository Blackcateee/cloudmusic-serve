package com.cloudmusic.song.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 歌曲实体类
 */
@Data
@TableName("t_songs")
public class Song {
    //id
    @TableId
    private Long songId;
    //歌曲链接
    private String songLink;
    //歌曲名字
    private String songName;
    //歌手
    private String songArtist;
    //歌曲所属专辑
    private String songAlbum;
    //歌词
    private String songLyric;
    //歌曲评论数
    private String songComment;
    //歌曲封面
    @TableField("song_albumPicture")
    private String songAlbumPicture;
    //歌曲播放链接
    private String songUrl;
    //歌曲标签
    private String songTags;
}
