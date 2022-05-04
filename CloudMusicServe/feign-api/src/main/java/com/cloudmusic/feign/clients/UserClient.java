package com.cloudmusic.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("userService")
public interface UserClient {
}
