package com.cloudmusic.song.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudmusic.song.entity.Singer;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.mapper.SingerMapper;
import com.cloudmusic.song.mapper.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class SingerService {

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private SongMapper songMapper;

    public List<Singer> selectAllSinger() {
        List<Singer> singers = singerMapper.selectList(null);
        for (Singer singer : singers) {
            singer.setSingerImg(singer.getSingerImg().replace("[\"","").replace("\"]",""));
        }
        return singers;
    }

    public HashMap<String, Object> selectSingerDetail(String singerId) {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.eq("singer_id", singerId);
        Singer singer = singerMapper.selectOne(singerQueryWrapper);
        singer.setSingerImg(singer.getSingerImg().replace("[\"","").replace("\"]",""));
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.eq("song_artist", singer.getSingerName());
        List<Song> songs = songMapper.selectList(songQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("singer", singer);
        map.put("songs", songs);
        return map;
    }
}
