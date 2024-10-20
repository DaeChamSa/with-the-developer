package com.developer.notiservice.client;

import com.developer.notiservice.config.FeignClientConfig;
import com.developer.notiservice.dto.ResponseUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="with-the-developer-msg-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @GetMapping("/user-service/user/auth/userCode")
    Long getCurrentUserCode();

    @GetMapping("/user-service/user/auth/userId")
    String getCurrentUserId();

    @GetMapping("/user-service/user/{userCode}")
    ResponseUserDTO findByUserCode(@PathVariable("userCode") Long userCode);
}
