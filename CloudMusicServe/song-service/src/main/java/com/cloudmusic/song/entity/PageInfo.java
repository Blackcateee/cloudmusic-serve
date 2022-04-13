package com.cloudmusic.song.entity;

import lombok.Data;

@Data
public class PageInfo {
    //当前页数
    private int pageNum;
    //每页item数
    private int pageSize;
    //查询条件
    private String query;
}
