package com.cloudmusic.feign.clients;

import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.feign.entity.SongVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("songservice")
public interface SongClients {

    @RequestMapping("/song/getDefaultSong")
    List<SongVO> getSong(@RequestBody QueryInfo queryInfo);
}
