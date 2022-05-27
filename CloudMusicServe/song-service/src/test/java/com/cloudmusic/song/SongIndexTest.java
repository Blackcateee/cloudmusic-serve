package com.cloudmusic.song;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cloudmusic.song.entity.Song;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

@SpringBootTest(classes = SongServiceApplication.class)
@RunWith(SpringRunner.class)
public class SongIndexTest {

    private RestHighLevelClient client;

    @Before
    public void init() {
        this.client = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://121.40.137.246:9200")));
    }

    @Test
    public void testExists() throws Exception{
        GetIndexRequest request = new GetIndexRequest("song");

        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testQuery() {
        SearchRequest searchRequest = new SearchRequest("song");

        searchRequest.source().query(QueryBuilders.matchQuery("songName", "Loser"));
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : response.getHits().getHits()) {
                String sourceAsString = hit.getSourceAsString();
                Song song = JSON.parseObject(sourceAsString, Song.class);
                System.out.println(song);
            }
        } catch (IOException e) {
            System.out.println("es服务端出错");
        }
    }

    @After
    public void close() throws Exception{
        this.client.close();
    }
}
