package com.developer.client;

import com.developer.client.dto.ResponseUserDTO;
import com.developer.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "with-the-developer-user-service", url = "localhost:8000", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @GetMapping("/user-service/user/userCode")
    Long responseUserCode();

    @GetMapping("/user-service/detail")
    ResponseEntity<ResponseUserDTO> userDetail();
}
