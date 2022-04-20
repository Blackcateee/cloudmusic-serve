package com.cloudmusic.song.util;

import com.cloudmusic.feign.entity.SongVO;
import com.cloudmusic.song.entity.Song;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface SongToSongVo {

    List<SongVO> domain2dto(List<Song> people);
}
