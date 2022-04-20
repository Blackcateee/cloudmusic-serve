package com.cloudmusic.feign.entity;

import lombok.Data;

/**
 * 歌曲实体类
 */
@Data
public class SongVO {
    //id
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
    private String songAlbumPicture;
    //歌曲播放链接
    private String songUrl;
    //歌曲标签
    private String songTags;
}
