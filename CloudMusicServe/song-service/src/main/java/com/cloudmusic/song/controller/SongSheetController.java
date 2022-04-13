package com.cloudmusic.song.controller;

import com.cloudmusic.song.entity.PageInfo;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.service.SongSheetService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
public class SongSheetController {

    @Resource
    private SongSheetService service;

    @RequestMapping("/song/listByAmount")
    public List<SongSheet> selectSongSheetByAmount() {
        return service.selectSongSheetByAmount();
    }

    @RequestMapping("/song/list")
    public List<SongSheet> selectSongSheet() {
        return service.selectSongSheet();
    }

    @RequestMapping("/song/listAll")
    public HashMap<String, Object> selectSongSheetAll(@RequestBody PageInfo pageInfo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sheet", service.selectSongSheetAll(pageInfo).getRecords());
        map.put("num", service.selectSongSheetAll(pageInfo).getTotal());
        return map;
    }
}
