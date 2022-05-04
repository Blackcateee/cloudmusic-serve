package com.cloudmusic.song.controller;

import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.feign.entity.SongSheetVO;
import com.cloudmusic.feign.entity.SongVO;
import com.cloudmusic.song.entity.PageInfo;
import com.cloudmusic.song.entity.ResultVO;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.service.SongService;
import com.cloudmusic.song.service.SongSheetService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
public class SongSheetController {

    @Resource
    private SongSheetService service;

    @Resource
    private SongService songService;

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

    @RequestMapping("/song/songListInSheet")
    public HashMap<String, Object> selectSongListInSheet(@RequestParam("listId") String listId) {
        return service.selectSongs(listId);
    }

    @RequestMapping("/song/getDefaultSong")
    public List<SongVO> selectSongs(@RequestBody QueryInfo queryInfo) {
        if(queryInfo.getMessage() == null) {
            return null;
        }
        return songService.selectDefaultSong(queryInfo);
    }

    @RequestMapping("/song/addSongSheet")
    public Long addSongSheet(@RequestParam String songSheetName, @RequestParam String userName) {
        return service.addSongSheet(songSheetName, userName);
    }

    @RequestMapping("/song/getUserSongSheet")
    public List<SongSheet> getUserSongSheet(@RequestParam("songSheetList") String songSheetList) {
        return service.getUserSongSheet(songSheetList);
    }

    @RequestMapping("/song/deleteSongSheet")
    public boolean deleteSongSheet(@RequestParam String songSheetId) {
        return service.deleteSongSheet(songSheetId);
    }

    @RequestMapping("/song/selectUserSongSheet")
    public List<SongSheetVO> selectUserSongSheet(@RequestParam("songSheetIdList") List<String> songSheetIdList) {
        return service.selectUserCollection(songSheetIdList);
    }

    @RequestMapping("/song/addSongIntoSongSheet")
    public ResultVO addSongIntoSongSheet(@RequestParam("songSheetId") String songSheetId, @RequestParam("songId") String songId) {
        return service.addSongIntoSongSheet(songSheetId, songId);
    }

    @RequestMapping("/song/getAllSongs")
    public HashMap<String, Object> getAllSongs(@RequestBody PageInfo pageInfo) {
        return songService.getAllSongs(pageInfo);
    }
 }


