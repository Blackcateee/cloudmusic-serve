package com.cloudmusic.song;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@MapperScan("com.cloudmusic.song.mapper")
public class SongServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SongServiceApplication.class, args);
    }
}
