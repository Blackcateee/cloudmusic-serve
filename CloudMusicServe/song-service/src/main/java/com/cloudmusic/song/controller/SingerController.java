package com.cloudmusic.song.controller;

import com.cloudmusic.song.entity.PageInfo;
import com.cloudmusic.song.entity.Singer;
import com.cloudmusic.song.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class SingerController {

    @Autowired
    private SingerService singerService;

    @RequestMapping("/song/getSingerList")
    public List<Singer> getSingerList() {
        return singerService.selectAllSinger();
    }

    @RequestMapping("/song/getSingerDetail")
    public HashMap<String, Object> getSingerDetail(@RequestParam("singerId") String singerId) {
        return singerService.selectSingerDetail(singerId);
    }

    @RequestMapping("/song/getAllSinger")
    public HashMap<String, Object> getAllSinger(@RequestBody PageInfo pageInfo) {
        return singerService.getAllSinger(pageInfo);
    }
}
