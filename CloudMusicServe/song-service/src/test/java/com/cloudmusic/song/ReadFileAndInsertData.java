package com.cloudmusic.song;

import com.alibaba.fastjson.JSON;
import com.cloudmusic.song.entity.Singer;
import com.cloudmusic.song.entity.Song;
import com.cloudmusic.song.entity.SongSheet;
import com.cloudmusic.song.mapper.SingerMapper;
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
import java.util.Random;

@SpringBootTest(classes = SongServiceApplication.class)
@RunWith(SpringRunner.class)
public class ReadFileAndInsertData {

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongSheetMapper songSheetMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Test
    public void readSinger() {
        String path = "D:\\coding\\singer.json";
        File file = new File(path);
        try(InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),StandardCharsets.UTF_8)) {
            BufferedReader bufferedReader = new BufferedReader((inputStreamReader));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(line);
                if(line.contains("}")) {
                    stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
                    Singer singerObject = JSON.parseObject(stringBuilder.toString(), Singer.class);
                    List<Long> strings = new ArrayList<>();
                    List<Singer> singers = singerMapper.selectList(null);
                    for (Singer singer : singers) {
                        strings.add(singer.getSingerId());
                    }
                    if (strings.contains(singerObject.getSingerId())) {
                        singerMapper.updateById(singerObject);
                    } else {
                        singerMapper.insert(singerObject);
                    }
                    stringBuilder.setLength(0);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readSongs() {
        String path = "D:\\coding\\songs.json";
        File jsonFile = new File(path);
        try (InputStreamReader streamReader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
            final BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder jsonString = new StringBuilder();
                jsonString.append(line);
                if (line.contains("}")) {
                    //删除json对象最后的逗号
                    jsonString.replace(jsonString.length() - 1, jsonString.length(), "");
                    Song song = JSON.parseObject(jsonString.toString(), Song.class);
                    List<Long> strings = new ArrayList<>();
                    final List<Song> songs = songMapper.selectList(null);
                    for (Song song1 : songs) {
                        strings.add(song1.getSongId());
                    }
                    if (strings.contains(song.getSongId())) {
                        songMapper.updateById(song);
                    } else {
                        songMapper.insert(song);
                    }
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
                    //删除json对象最后的逗号
                    jsonString.replace(jsonString.length() - 1, jsonString.length(), "");
                    SongSheet songSheet = JSON.parseObject(jsonString.toString(), SongSheet.class);
                    List<Long> strings = new ArrayList<>();
                    final List<SongSheet> songSheets = songSheetMapper.selectList(null);
                    for (SongSheet songSheet1 : songSheets) {
                        strings.add(songSheet1.getListId());
                    }
                    if (strings.contains(songSheet.getListId())) {
                        songSheetMapper.updateById(songSheet);
                    } else {
                        songSheetMapper.insert(songSheet);
                    }
                    jsonString.setLength(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
