package com.cloudmusic.feign.clients;

import com.cloudmusic.feign.entity.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@FeignClient("userservice")
public interface UserClient {
    @RequestMapping("/user/getAllComment")
    public HashMap<String, Object> getAllComment(@RequestBody PageInfo pageInfo);

    @RequestMapping("/user/getAllUser")
    public HashMap<String, Object> getAllUser(@RequestBody PageInfo pageInfo);
}
