package com.cloudmusic.song;

import com.alibaba.fastjson.JSON;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.mapper.SongMapper;
import com.cloudmusic.song.mapper.SongSheetMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = SongServiceApplication.class)
@RunWith(SpringRunner.class)
public class SongDocTest {

    private RestHighLevelClient client;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongSheetMapper songSheetMapper;

    @Before
    public void init() {
        this.client = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://121.40.137.246:9200")));
    }

    @Test
    public void testInsert() throws Exception{

        Song song = songMapper.selectById(66253);
        IndexRequest request = new IndexRequest().index("song").id(song.getSongId().toString());

        request.source(JSON.toJSONString(song), XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testGet() throws Exception{
        GetRequest getRequest = new GetRequest().index("song").id("66253");

        GetResponse res = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(res.getSourceAsString());
    }

    @Test
    public void testDelete() throws Exception {
        DeleteRequest request = new DeleteRequest().index("song").id("66253");

        client.delete(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testBulk() throws Exception{
        BulkRequest bulkRequest = new BulkRequest();

        List<Song> songs = songMapper.selectList(null);
        for (Song song : songs) {
            song.setSongAlbumPicture(song.getSongAlbumPicture().replace("[\"", "").replace("\"]", ""));
            bulkRequest.add(new IndexRequest()
                    .index("song")
                    .id(song.getSongId().toString())
                    .source(JSON.toJSONString(song), XContentType.JSON));
        }

        client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    @Test
    public void insertSongSheetToES() {
        BulkRequest bulkRequest = new BulkRequest();

        List<SongSheet> songSheets = songSheetMapper.selectList(null);
        for (SongSheet songSheet : songSheets) {
            songSheet.setListImg(songSheet.getListImg().replace("[\"", "").replace("\"]", ""));
            bulkRequest.add(new IndexRequest()
                    .index("songsheet")
                    .id(songSheet.getListId().toString())
                    .source(JSON.toJSONString(songSheet), XContentType.JSON));
            try {
                client.bulk(bulkRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("es服务出错");
            }
        }
    }

    @Test
    public void searchSongSheet() {
        SearchRequest searchRequest = new SearchRequest("songsheet");

        searchRequest
                .source()
                .query(QueryBuilders.matchQuery("listTags", "[\"轻音乐\",\"学习\",\"安静\"]"));

        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : search.getHits().getHits()) {
                String sourceAsString = hit.getSourceAsString();

                SongSheet songSheet = JSON.parseObject(sourceAsString, SongSheet.class);
                System.out.println(songSheet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void close() throws Exception{
        this.client.close();
    }
}
