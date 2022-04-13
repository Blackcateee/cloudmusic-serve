package com.cloudmusic.song.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmusic.song.entity.SongSheet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SongSheetMapper extends BaseMapper<SongSheet> {
}
