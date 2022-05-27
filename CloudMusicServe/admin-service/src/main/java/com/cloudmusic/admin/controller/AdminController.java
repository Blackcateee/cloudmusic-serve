package com.cloudmusic.admin.controller;

import com.cloudmusic.feign.clients.UserClient;
import com.cloudmusic.feign.entity.PageInfo;
import com.cloudmusic.admin.entity.ResultVO;
import com.cloudmusic.admin.service.AdminService;
import com.cloudmusic.feign.clients.SongClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SongClients songClients;

    @Autowired
    private UserClient userClient;

    @RequestMapping("/admin/login")
    public ResultVO login(@RequestParam("account") String account, @RequestParam("pwd") String pwd) {
        return adminService.login(account, pwd);
    }

    @RequestMapping("/admin/getAllSongs")
    public HashMap<String, Object> getAllSongs(@RequestBody PageInfo pageInfo) {
        return songClients.getAllSongs(pageInfo);
    }
    
    @RequestMapping("/admin/getAllSongSheet")
    public HashMap<String, Object> getAllSonSheet(@RequestBody PageInfo pageInfo) {
        return songClients.getAllSongSheet(pageInfo);
    }

    @RequestMapping("/admin/getAllSinger")
    public HashMap<String, Object> getAllSinger(@RequestBody PageInfo pageInfo) {
        return songClients.getAllSinger(pageInfo);
    }

    @RequestMapping("/admin/getAllUser")
    public HashMap<String, Object> getAllUser(@RequestBody PageInfo pageInfo) {
        return userClient.getAllUser(pageInfo);
    }

    @RequestMapping("/admin/getAllComment")
    public HashMap<String, Object> getAllComment(@RequestBody PageInfo pageInfo) {
        return userClient.getAllComment(pageInfo);
    }
}
