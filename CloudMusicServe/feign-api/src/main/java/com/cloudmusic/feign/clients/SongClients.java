package com.cloudmusic.feign.clients;

import com.cloudmusic.feign.entity.PageInfo;
import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.feign.entity.SongSheetVO;
import com.cloudmusic.feign.entity.SongVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@FeignClient("songservice")
public interface SongClients {

    @RequestMapping("/song/getDefaultSong")
    List<SongVO> getSong(@RequestBody QueryInfo queryInfo);

    @RequestMapping("/song/addSongSheet")
    Long addSongSheet(@RequestParam String songSheetName, @RequestParam String userName);

    @RequestMapping("/song/deleteSongSheet")
    boolean deleteSongSheet(@RequestParam String songSheetId);

    @RequestMapping("/song/selectUserSongSheet")
    public List<SongSheetVO> selectUserSongSheet(@RequestBody List<String> songSheetId);

    @RequestMapping("/song/getAllSongs")
    public HashMap<String, Object> getAllSongs(@RequestBody PageInfo pageInfo);

    @RequestMapping("/song/listAll")
    public HashMap<String, Object> getAllSongSheet(@RequestBody PageInfo pageInfo);

    @RequestMapping("/song/getAllSinger")
    public HashMap<String, Object> getAllSinger(@RequestBody PageInfo pageInfo);

    @RequestMapping("/song/searchSongSheetByTags")
    public List<SongSheetVO> searchSongSheetByTags(@RequestParam("tags") String tags);
}
