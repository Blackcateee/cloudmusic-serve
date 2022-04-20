package com.cloudmusic.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResultVO {
    //是否成功
    private boolean success;
    //话术
    private String message;
}
