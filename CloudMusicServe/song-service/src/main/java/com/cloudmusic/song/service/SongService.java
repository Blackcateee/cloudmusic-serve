package com.cloudmusic.song.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.feign.entity.SongVO;
import com.cloudmusic.song.delayedTask.DeleteNoUseImage;
import com.cloudmusic.song.entity.PageInfo;
import com.cloudmusic.song.entity.ResultVO;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.mapper.SongMapper;
import com.cloudmusic.song.util.SongToSongVo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongService {

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongToSongVo songToSongVo;

    @Resource
    private DeleteNoUseImage deleteNoUseImage;

    private final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://121.40.137.246:9200")));

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

    public Song getSongInfo(String songId) {
        Song song = songMapper.selectById(songId);
        return song;
    }

    public ResultVO insertSong(Song song) {
        song.setSongId(null);
        songMapper.insert(song);
        IndexRequest indexRequest = new IndexRequest().index("song").id(song.getSongId().toString());

        indexRequest.source(JSON.toJSONString(song), XContentType.JSON);
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            songMapper.deleteById(song.getSongId());
            return ResultVO.builder().success(false).message("es服务出现问题，添加失败").build();
        }
        deleteNoUseImage.deleteItem(song.getSongAlbumPicture().replace("http://121.40.137.246:9000/cloudmusic/", ""));
        return ResultVO.builder().success(true).message("添加成功").build();
    }

    public ResultVO updateSong(Song song) {
        songMapper.updateById(song);
        IndexRequest indexRequest = new IndexRequest().index("song").id(song.getSongId().toString());

        indexRequest.source(JSON.toJSONString(song), XContentType.JSON);
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            songMapper.deleteById(song.getSongId());
            return ResultVO.builder().success(false).message("es服务出现问题，添加失败").build();
        }
        deleteNoUseImage.deleteItem(song.getSongAlbumPicture().replace("http://121.40.137.246:9000/cloudmusic/", ""));
        return ResultVO.builder().success(true).message("修改成功").build();
    }

    public HashMap<String, Object> search(String state) {
        List<Song> songs = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        SearchRequest searchRequest = new SearchRequest("song");

        searchRequest.source().query(QueryBuilders
                .matchQuery("songName", state))
                .highlighter(new HighlightBuilder()
                        .field("songName")
                        .requireFieldMatch(false)
                        .postTags("</em>").preTags("<em>"));
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : response.getHits().getHits()) {
                String sourceAsString = hit.getSourceAsString();
                Song song = JSON.parseObject(sourceAsString, Song.class);
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlightField = highlightFields.get("songName");
                String songName = highlightField.getFragments()[0].toString();
                song.setSongName(songName);
                songs.add(song);
            }
            map.put("songs", songs);
            map.put("message", "查询成功");
            return map;
        } catch (IOException e) {
            System.out.println("es服务端出错");
            map.put("message", "es服务端出错");
            return map;
        }
    }
}
