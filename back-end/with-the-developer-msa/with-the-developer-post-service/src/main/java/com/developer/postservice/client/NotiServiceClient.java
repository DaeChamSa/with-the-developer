package com.developer.postservice.client;

import com.developer.postservice.config.FeignClientConfig;
import com.developer.postservice.dto.NotiCommentCreateDTO;
import com.developer.postservice.dto.NotiMsgCreateDTO;
import com.developer.postservice.dto.NotiRecruitCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="with-the-developer-noti-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface NotiServiceClient {

    @PostMapping("/noti-service/noti/msg")
    void addMsgEvent(NotiMsgCreateDTO notiMsgCreateDTO);

    @PostMapping("/noti-service/noti/recruit/appr")
    void addAcceptEvent(NotiRecruitCreateDTO notiRecruitCreateDTO);

    @PostMapping("/noti-service/noti/recruit/reject")
    void addRejectEvent(NotiRecruitCreateDTO notiRecruitCreateDTO);

    @PostMapping("/noti-service/noti/comment")
    void addCommentEvent(NotiCommentCreateDTO notiCommentCreateDTO);
}
