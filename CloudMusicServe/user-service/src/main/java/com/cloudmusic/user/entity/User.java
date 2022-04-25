package com.cloudmusic.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.codec.language.bm.Rule;

@Data
@TableName("t_users")
public class User {
    //id
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    //用户权限
    private int userAccess;
    //用户名
    private String userName;
    //用户邮箱
    private String userEmail;
    //用户密码
    private String userPwd;
    //用户头像
    private String userImage;
    //用户喜欢
    private String userLike;
    //用户收藏的歌单
    private String userCollection;
    //用户收藏的歌手
    private String userSinger;
    //用户的歌单
    @TableField("user_songSheet")
    private String userSongSheet;
    //用户昵称
    @TableField("user_nickName")
    private String userNickname;
}
