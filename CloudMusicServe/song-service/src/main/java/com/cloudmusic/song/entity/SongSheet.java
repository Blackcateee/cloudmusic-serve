package com.cloudmusic.song.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 歌单实体类
 */
@Data
@TableName("t_lists")
public class SongSheet {
    //歌单id
    @TableId
    private long listId;
    //歌单链接
    private String listLink;
    //歌单标题
    private String listTitle;
    //歌单封面
    private String listImg;
    //歌单作者
    private String listAuthor;
    //歌单播放量
    private long listAmount;
    //歌单标签
    private String listTags;
    //歌单收藏量
    private String listCollection;
    //歌单分享量
    private long listForward;
    //歌单评论数
    private long listComment;
    //歌单描述
    private String listDescription;
    //歌单里的歌曲列表
    private String listSongs;
}
