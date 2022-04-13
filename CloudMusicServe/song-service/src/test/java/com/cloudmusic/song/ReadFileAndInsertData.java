package com.cloudmusic.song;

import com.alibaba.fastjson.JSON;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.mapper.SongMapper;
import com.cloudmusic.song.mapper.SongSheetMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = SongServiceApplication.class)
@RunWith(SpringRunner.class)
public class ReadFileAndInsertData {

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongSheetMapper songSheetMapper;

    @Test
    public void readSongs() {
        String path = "D:\\coding\\data\\songs.json";
        File jsonFile = new File(path);
        try (InputStreamReader streamReader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
            final BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder jsonString = new StringBuilder();
                jsonString.append(line);
                if (line.contains("}")) {
                    jsonString.replace(jsonString.length() - 1, jsonString.length(), "");
                    Song song = JSON.parseObject(jsonString.toString(), Song.class);
                    List<Long> strings = new ArrayList<>();
                    final List<Song> songs = songMapper.selectList(null);
                    for (Song song1 : songs) {
                        strings.add(song1.getSongId());
                    }
                    if(strings.contains(song.getSongId())) {
                        songMapper.updateById(song);
                    }
                    songMapper.insert(song);
                    jsonString.setLength(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readSongSheets() {
        String path = "D:\\coding\\data\\lists.json";
        File jsonFile = new File(path);
        try (InputStreamReader streamReader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
            final BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder jsonString = new StringBuilder();
                jsonString.append(line);
                if (line.contains("}")) {
                    jsonString.replace(jsonString.length() - 1, jsonString.length(), "");
                    SongSheet songSheet = JSON.parseObject(jsonString.toString(), SongSheet.class);
                    songSheetMapper.insert(songSheet);
                    jsonString.setLength(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
