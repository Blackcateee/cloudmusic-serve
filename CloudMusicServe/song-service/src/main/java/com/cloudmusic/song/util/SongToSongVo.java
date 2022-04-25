package com.cloudmusic.song.util;

import com.cloudmusic.feign.entity.SongSheetVO;
import com.cloudmusic.feign.entity.SongVO;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.entity.SongSheet;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface SongToSongVo {

    List<SongVO> domain2dto(List<Song> songList);

    List<SongSheetVO> songSheetListToSongSheetVOList(List<SongSheet> songSheetList);
}
