package com.cloudmusic.user.controller;

import com.cloudmusic.user.entity.PageInfo;
import com.cloudmusic.user.entity.ResultVO;
import com.cloudmusic.user.entity.User;
import com.cloudmusic.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/user/register")
    public ResultVO register(@RequestBody User user) {
        return userService.insertUser(user);
    }

    @RequestMapping("/user/login")
    public ResultVO login(@RequestBody User user) {
        return userService.login(user);
    }

    @RequestMapping("/user/defaultSong")
    public HashMap<String, Object> getDefaultSong(@RequestParam("userName") String userName) {
        return userService.getDefaultSong(userName);
    }

    @RequestMapping("/user/getUserInfo")
    public User getUserInfo(String userName) {
        return userService.getUserInfo(userName);
    }

    @RequestMapping("/user/addSongSheet")
    public String addSongSheet(@RequestParam("songSheetName") String songSheetName, @RequestParam("userName") String userName) {
        return userService.addSongSheet(songSheetName, userName);
    }

    @RequestMapping("/user/deleteSongSheet")
    public String deleteSongSheet(@RequestParam("userName") String username, @RequestParam("songSheetId") String songSheetId) {
        return userService.deleteSongSheet(username, songSheetId);
    }

    @RequestMapping("/user/addUserCollection")
    public ResultVO addUserCollection(@RequestParam("userName") String username, @RequestParam("songSheetId") String songSheetId) {
        return userService.addUserCollection(username, songSheetId);
    }

    @RequestMapping("/user/getAllUser")
    public HashMap<String, Object> getAllUser(@RequestBody PageInfo pageInfo) {
        return userService.getAllUser(pageInfo);
    }

    @RequestMapping("/user/uploadAvatar")
    public String uploadAvatar(@RequestPart MultipartFile[] file) {
        return  userService.uploadAvatar(file);
    }

    @RequestMapping("/user/updateUser")
    public ResultVO updateUser(@RequestParam("userName") String userName, @RequestParam("nickName") String nickName, @RequestParam("userImage") String userImage) {
        return userService.updateUser(userName, nickName, userImage);
    }

    @RequestMapping("/user/updatePassword")
    public ResultVO updatePassword(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        return userService.updatePassword(userName, password);
    }
}
