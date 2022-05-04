package com.cloudmusic.admin.controller;

import com.cloudmusic.admin.AdminServiceApplication;
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

    @RequestMapping("/admin/login")
    public ResultVO login(@RequestParam("account") String account, @RequestParam("pwd") String pwd) {
        return adminService.login(account, pwd);
    }

    @RequestMapping("/admin/getAllSongs")
    public HashMap<String, Object> getAllSongs(@RequestBody PageInfo pageInfo) {
        return songClients.getAllSongs(pageInfo);
    }

}
