package com.cloudmusic.song.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudmusic.feign.entity.SongSheetVO;
import com.cloudmusic.feign.util.StringToListUtil;
import com.cloudmusic.song.entity.PageInfo;
import com.cloudmusic.song.entity.ResultVO;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.mapper.SongSheetMapper;
import com.cloudmusic.song.util.SongToSongVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongSheetService {

    @Autowired
    private SongSheetMapper songSheetMapper;

    @Autowired
    private SongService songService;

    @Autowired
    private SongToSongVo songToSongVo;

    //根据歌单播放量排序返回
    public List<SongSheet> selectSongSheetByAmount() {
        QueryWrapper<SongSheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("list_amount");
        List<SongSheet> songSheets = songSheetMapper.selectList(queryWrapper).subList(0, 4);
        for (SongSheet songSheet : songSheets) {
            songSheet.setListImg(songSheet.getListImg().replace("[\"", "").replace("\"]", ""));
        }
        return songSheets;
    }

    public List<SongSheet> selectSongSheet() {
        List<SongSheet> songSheets = songSheetMapper.selectList(null).subList(0, 8);
        for (SongSheet songSheet : songSheets) {
            songSheet.setListImg(songSheet.getListImg().replace("[\"", "").replace("\"]", ""));
        }
        return songSheets;
    }

    public Page<SongSheet> selectSongSheetAll(PageInfo pageInfo) {
        Page<SongSheet> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        Page<SongSheet> songSheetPage = songSheetMapper.selectPage(page, null);
        for (SongSheet songSheet : songSheetPage.getRecords()) {
            songSheet.setListImg(songSheet.getListImg().replace("[\"", "").replace("\"]", ""));
        }
        return songSheetPage;
    }

    public HashMap<String, Object> selectSongs(String listId) {
        //创建map存储歌单数据极其所含歌曲数据
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        songSheetQueryWrapper.eq("list_id", listId);
        SongSheet songSheet = songSheetMapper.selectOne(songSheetQueryWrapper);
        songSheet.setListImg(songSheet.getListImg().replace("[\"", "").replace("\"]", ""));
        map.put("songSheet", songSheet);
        List<String> songList = StringToListUtil.StringToList(songSheet.getListSongs());
        List<Song> songs = songService.selectSongsInSheet(songList);
        map.put("songs", songs);
        return map;
    }

    public Long addSongSheet(String songSheetName, String userName) {
        SongSheet songSheet = new SongSheet();
        songSheet.setListAuthor(userName);
        songSheet.setListTitle(songSheetName);
        songSheet.setListImg("[\"https://p1.music.126.net/jWE3OEZUlwdz0ARvyQ9wWw==/109951165474121408.jpg?param=200y200\"]");
        songSheetMapper.insert(songSheet);
        return songSheet.getListId();
    }

    public List<SongSheet> getUserSongSheet(String songSheetList) {
        String strip = StringUtils.strip(songSheetList, "[]");
        List<String> sheetList = Arrays.stream(strip.split(",")).collect(Collectors.toList());
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        songSheetQueryWrapper.in("list_id", sheetList);
        List<SongSheet> songSheets = songSheetMapper.selectList(songSheetQueryWrapper);
        for (SongSheet songSheet : songSheets) {
            songSheet.setListImg(songSheet.getListImg().replace("[\"", "").replace("\"]", ""));
        }
        return songSheets;
    }

    public boolean deleteSongSheet(String songSheetId) {
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        songSheetQueryWrapper.eq("list_id", songSheetId);
        int delete = songSheetMapper.delete(songSheetQueryWrapper);
        return delete != 0;
    }

    public List<SongSheetVO> selectUserCollection(List<String> songSheetIdList) {
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        songSheetQueryWrapper.in("list_id", songSheetIdList);
        List<SongSheetVO> songSheetVOS = songToSongVo.songSheetListToSongSheetVOList(songSheetMapper.selectList(songSheetQueryWrapper));
        for (SongSheetVO songSheetVO : songSheetVOS) {
            songSheetVO.setListImg(songSheetVO.getListImg().replace("[\"", "").replace("\"]", ""));
        }
        return songSheetVOS;
    }

    public ResultVO addSongIntoSongSheet(String songSheetId, String songId) {
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        songSheetQueryWrapper.eq("list_id", songSheetId);
        SongSheet songSheet = songSheetMapper.selectOne(songSheetQueryWrapper);
        String listSongs = songSheet.getListSongs();
        if (listSongs.contains(songId)) {
            return ResultVO.builder().message("不能重复添加").success(false).build();
        }
        if(StringUtils.isNotBlank(listSongs)) {
            List<String> strings = StringToListUtil.StringToList(listSongs);
            strings.add("," + songId);
            songSheet.setListSongs(strings.toString().replaceAll(" ",""));
            songSheetMapper.updateById(songSheet);
            return ResultVO.builder().message("添加成功").success(true).build();
        } else  {
            songSheet.setListSongs(songId);
            songSheetMapper.updateById(songSheet);
            return ResultVO.builder().message("添加成功").success(true).build();
        }
    }
}
