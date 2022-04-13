package com.cloudmusic.song.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {

    @RequestMapping("/song/test")
    public String test() {
        return "test success";
    }
}
