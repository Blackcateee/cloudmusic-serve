package com.cloudmusic.song.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.feign.entity.SongVO;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.mapper.SongMapper;
import com.cloudmusic.song.util.SongToSongVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
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
}
