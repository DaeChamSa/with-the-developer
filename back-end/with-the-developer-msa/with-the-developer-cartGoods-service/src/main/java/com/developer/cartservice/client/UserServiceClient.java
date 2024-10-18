package com.developer.cartservice.client;

import com.developer.cartservice.config.FeignClientConfig;
import com.developer.cartservice.dto.ResponseUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name="with-the-developer-user-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @GetMapping("/user-service/user/auth/userCode")
    Long getCurrentUserCode();

    @GetMapping("/user-service/user/auth/userId")
    String getCurrentUserId();

    @GetMapping("/user-service/user/{userCode}")
    ResponseUserDTO findByUserCode(@PathVariable("userCode") Long userCode);

    @GetMapping("/user-service/user/byUserId")
    ResponseUserDTO findByUserID(@RequestParam("userId") String userId);
}
