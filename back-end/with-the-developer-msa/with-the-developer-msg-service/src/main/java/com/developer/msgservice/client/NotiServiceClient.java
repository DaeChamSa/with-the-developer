package com.developer.msgservice.client;

import com.developer.msgservice.config.FeignClientConfig;
import com.developer.msgservice.dto.NotiMsgCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="with-the-developer-noti-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface NotiServiceClient {

    @PostMapping("noti-service/noti/msg")
    void addMsgEvent(NotiMsgCreateDTO notiMsgCreateDTO);
}
