package com.developer.msgservice.client;

import com.developer.msgservice.config.FeignClientConfig;
import com.developer.msgservice.dto.ResponseUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="with-the-developer-msg-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @GetMapping("/user-service/user/auth/userCode")
    Long getCurrentUserCode();

    @GetMapping("/user-service/user/auth/userId")
    String getCurrentUserId();

    @GetMapping("/user-service/user/{userCode}")
    ResponseUserDTO findByUserCode(@PathVariable("userCode") Long userCode);
}

