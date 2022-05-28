package com.cloudmusic.song.controller;

import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.feign.entity.SongSheetVO;
import com.cloudmusic.feign.entity.SongVO;
import com.cloudmusic.feign.util.StringToListUtil;
import com.cloudmusic.song.delayedTask.DeleteNoUseImage;
import com.cloudmusic.song.entity.PageInfo;
import com.cloudmusic.song.entity.ResultVO;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.service.SongService;
import com.cloudmusic.song.service.SongSheetService;
import com.cloudmusic.song.util.MinioUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class SongSheetController {

    @Resource
    private SongSheetService service;

    @Resource
    private SongService songService;

    @Resource
    private MinioUtil minioUtil;

    @Resource
    private DeleteNoUseImage deleteNoUseImage;

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
        if (queryInfo.getMessage() == null) {
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
    public List<SongSheetVO> selectUserSongSheet(@RequestBody List<String> songSheetIdList) {
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

    @RequestMapping("/song/getSongInfo")
    public Song getSongInfo(@RequestParam("songId") String songId) {
        return songService.getSongInfo(songId);
    }

    @RequestMapping("/song/upload")
    public String upload(MultipartFile[] file) {
        List<String> images = minioUtil.upload(file, "images");
        deleteNoUseImage.addItem(images.get(0));
        return images.get(0);
    }

    @RequestMapping("/song/insertSong")
    public ResultVO insertOrUpdateSong(@RequestBody Song song) {
        if (Objects.isNull(song.getSongId())) {
            return songService.insertSong(song);
        } else {
            return songService.updateSong(song);
        }
    }

    @RequestMapping("/song/uploadSong")
    public String uploadSong(MultipartFile[] file) {
        List<String> song = minioUtil.upload(file, "music");
        return song.get(0);
    }

    @RequestMapping("/song/search")
    public HashMap<String, Object> search(@RequestParam String state) {
        return songService.search(state);
    }

    @RequestMapping("/song/selectSongsInSheet")
    public List<Song> selectSongsInSheet(@RequestParam String songList) {
        List<String> songIdList = StringToListUtil.StringToList(songList);
        return songService.selectSongsInSheet(songIdList);
    }

    @RequestMapping("/song/insertSongSheet")
    public ResultVO insertSongSheet(@RequestBody SongSheet songSheet) {
        if (Objects.isNull(songSheet.getListId())) {
            return service.insertSongSheet(songSheet);
        } else {
            return service.updateSongSheet(songSheet);
        }
    }

    @RequestMapping("/song/searchSongSheetByTags")
    public List<SongSheet> searchSongSheetByTags(@RequestParam("tags") String tags) {
        return service.searchSongSheetByTags(tags);
    }
}


