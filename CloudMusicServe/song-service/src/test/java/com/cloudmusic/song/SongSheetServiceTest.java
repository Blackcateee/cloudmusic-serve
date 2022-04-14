package com.cloudmusic.song;

import com.cloudmusic.song.service.SongSheetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SongServiceApplication.class)
@RunWith(SpringRunner.class)
public class SongSheetServiceTest {

    @Autowired
    private SongSheetService songSheetService;

    @Test
    public void test() {
        System.out.println(songSheetService.selectSongs("167966646"));
    }
}
