package com.developer.client;

import com.developer.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "with-the-developer-user-service", url = "localhost:8000", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @GetMapping("/user-service/user/user-check/{userCode}")
    boolean userCheck(@PathVariable Long userCode);

    @GetMapping("/user-service/user-isResNoti/{userCode}")
    boolean userIsResNoti(@PathVariable Long userCode);

    @GetMapping("/user-id-check/{userId}")
    boolean userIdCheck(@PathVariable String userId);
}
