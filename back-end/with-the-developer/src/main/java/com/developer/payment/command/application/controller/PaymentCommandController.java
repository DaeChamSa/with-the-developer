package com.developer.payment.command.application.controller;

import com.developer.payment.command.application.dto.PaymentCallbackRequest;
import com.developer.payment.command.application.dto.RequestPayDTO;
import com.developer.payment.command.application.service.PaymentCommandService;
import com.developer.user.security.SecurityUtil;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentCommandController {

    private final PaymentCommandService paymentService;

    @Value("${iam.ipm.code}")
    private String ipmKey;

    @GetMapping("/payment/{orderUid}")
    public String payment(@PathVariable(name = "orderUid") String orderUid,
                                                 Model model){

        log.info("로깅 확인 payment");
        RequestPayDTO requestPayDTO = paymentService.findRequestDTO(orderUid);
        model.addAttribute("requestDto", requestPayDTO);
        model.addAttribute("ipmKey", ipmKey);

        return "payment";
    }

    @ResponseBody
    @PostMapping("/payment")
    public ResponseEntity<IamportResponse<Payment>> validationPayment(@RequestBody PaymentCallbackRequest request){

        log.info("로깅 확인 validationPayment");
        IamportResponse<Payment> paymentIamportResponse = paymentService.paymentByCallback(request);

        log.info("결제 응답 = {}", paymentIamportResponse.getResponse().toString());

        return new ResponseEntity<>(paymentIamportResponse, HttpStatus.OK);
    }

    @GetMapping("/payment/cancel/{paymentUid}")
    public ResponseEntity<?> cancelPayment(@PathVariable(name = "paymentUid") String paymentUid){

        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        paymentService.cancelPayment(currentUserCode, paymentUid);

        return ResponseEntity.ok().build();
    }
}
