package com.cloudmusic.song.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.feign.entity.SongVO;
import com.cloudmusic.song.entity.PageInfo;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.mapper.SongMapper;
import com.cloudmusic.song.util.SongToSongVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongToSongVo songToSongVo;

    public List<Song> selectSongsInSheet(List<String> songList) {
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.in("song_id", songList);
        List<Song> songsList = songMapper.selectList(songQueryWrapper);
        return songsList;
    }

    public List<SongVO> selectDefaultSong(QueryInfo queryInfo) {
        String message = queryInfo.getMessage();
        String[] strings = message.replace("[\"", "").replace("\"]", "").replaceAll("\"", "").split(",");
        List<String> songIdList = Arrays.asList(strings);
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.in("song_id", songIdList);
        List<Song> songList = songMapper.selectList(songQueryWrapper);
        List<SongVO> songVOS = songToSongVo.domain2dto(songList);
        return  songVOS;
    }

    public HashMap<String, Object> getAllSongs(PageInfo pageInfo) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Song> songQueryWrapper = null;
        if(StringUtils.isNotBlank(pageInfo.getQuery())) {
            songQueryWrapper = new QueryWrapper<>();
            songQueryWrapper.like("song_name", pageInfo.getQuery());
        }
        Page<Song> songPage = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        Page<Song> songPageList = songMapper.selectPage(songPage, songQueryWrapper);
        List<Song> songList = songPageList.getRecords();
        for (Song song : songList) {
            song.setSongAlbumPicture(song.getSongAlbumPicture().replace("[\"", "").replace("\"]", ""));
        }
        map.put("songs", songList);
        map.put("total", songPageList.getTotal());
        return map;
    }
}
