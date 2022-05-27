package com.cloudmusic.user.controller;

import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.user.entity.CommentDTO;
import com.cloudmusic.user.entity.CommentVO;
import com.cloudmusic.user.entity.PageInfo;
import com.cloudmusic.user.entity.ResultVO;
import com.cloudmusic.user.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/user/insertComment")
    public ResultVO insertComment(@RequestBody CommentDTO commentDTO) {
        return commentService.insertComment(commentDTO);
    }

    @RequestMapping("/user/getComment")
    public HashMap<String, Object> getComment(@RequestBody PageInfo pageInfo) {
        return commentService.getComment(pageInfo);
    }

    @RequestMapping("/user/getAllComment")
    public HashMap<String, Object> getAllComment(@RequestBody PageInfo pageInfo) {
        return commentService.getComment(pageInfo);
    }
}
