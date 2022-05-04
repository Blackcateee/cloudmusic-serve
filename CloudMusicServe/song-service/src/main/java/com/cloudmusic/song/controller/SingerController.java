package com.cloudmusic.song.controller;

import com.cloudmusic.song.entity.Singer;
import com.cloudmusic.song.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
