package com.cloudmusic.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.user.entity.*;
import com.cloudmusic.user.mapper.CommentMapper;
import com.cloudmusic.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    public ResultVO insertComment(CommentDTO commentDTO) {
        Comment commentEntity = new Comment();
        if(commentDTO.getSongId() != 0) {
            commentEntity.setSongId(commentDTO.getSongId());
            commentEntity.setUserName(commentDTO.getUserName());
            commentEntity.setComment(commentDTO.getComment());
            commentMapper.insert(commentEntity);
            return ResultVO.builder().success(true).message("评论成功").build();
        } else if (commentDTO.getSongSheetId() != 0) {
            commentEntity.setSongSheetId(commentDTO.getSongSheetId());
            commentEntity.setUserName(commentDTO.getUserName());
            commentEntity.setComment(commentDTO.getComment());
            commentMapper.insert(commentEntity);
            return ResultVO.builder().success(true).message("评论成功").build();
        } else {
            return ResultVO.builder().success(false).message("评论失败").build();
        }
    }

    public HashMap<String, Object> getComment(PageInfo pageInfo) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("song_id", pageInfo.getQuery()).or().eq("songSheet_id", pageInfo.getQuery());
        Page<Comment> commentPage = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        Page<Comment> selectPage = commentMapper.selectPage(commentPage, commentQueryWrapper);
        List<CommentVO> commentVOS = new ArrayList<>();
        for (Comment record : selectPage.getRecords()) {
            CommentVO commentVO = new CommentVO();
            User userInfo = userService.getUserInfo(record.getUserName());
            commentVO.setUserImg(userInfo.getUserImage());
            commentVO.setUserNickName(userInfo.getUserNickname());
            commentVO.setComment(record.getComment());
            commentVO.setCommentTime(record.getInsertTime());
            commentVOS.add(commentVO);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("comments", commentVOS);
        map.put("total", selectPage.getTotal());
        return map;
    }
}
