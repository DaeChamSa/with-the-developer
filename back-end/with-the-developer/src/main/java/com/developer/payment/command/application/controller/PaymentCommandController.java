package com.developer.payment.command.application.controller;

import com.developer.payment.command.application.dto.PaymentCallbackRequest;
import com.developer.payment.command.application.dto.RequestPayDTO;
import com.developer.payment.command.application.service.PaymentCommandService;
import com.developer.user.security.SecurityUtil;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "payment", description = "결제 API")
@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentCommandController {

    private final PaymentCommandService paymentService;

    @Value("${iam.ipm.code}")
    private String ipmKey;

    @GetMapping("/payment/{orderUid}")
    @Operation(summary = "결제 정보 생성", description = "주문한 내역을 토대로 결제 정보를 생성합니다.")
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
    @Operation(summary = "결제 유효성 검사", description = "결제하려는 내역과 주문 내역이 같은지 확인합니다.")
    public ResponseEntity<IamportResponse<Payment>> validationPayment(@RequestBody PaymentCallbackRequest request){

        log.info("로깅 확인 validationPayment");
        IamportResponse<Payment> paymentIamportResponse = paymentService.paymentByCallback(request);

        log.info("결제 응답 = {}", paymentIamportResponse.getResponse().toString());

        return new ResponseEntity<>(paymentIamportResponse, HttpStatus.OK);
    }

    @GetMapping("/payment/cancel/{paymentUid}")
    @Operation(summary = "결제 취소", description = "결제했던 내역을 취소합니다.")
    public ResponseEntity<?> cancelPayment(@PathVariable(name = "paymentUid") String paymentUid){

        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        paymentService.cancelPayment(currentUserCode, paymentUid);

        return ResponseEntity.ok().build();
    }
}
