package com.cloudmusic.song.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.mapper.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongMapper songMapper;

    public List<Song> selectSongsInSheet(List<String> songList) {
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.in("song_id", songList);
        List<Song> songsList = songMapper.selectList(songQueryWrapper);
        return songsList;
    }
}
