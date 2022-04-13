package com.cloudmusic.song.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudmusic.song.entity.PageInfo;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.mapper.SongSheetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongSheetService {

    @Autowired
    private SongSheetMapper songSheetMapper;

    public List<SongSheet> selectSongSheetByAmount() {
        QueryWrapper<SongSheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("list_amount");
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
}
