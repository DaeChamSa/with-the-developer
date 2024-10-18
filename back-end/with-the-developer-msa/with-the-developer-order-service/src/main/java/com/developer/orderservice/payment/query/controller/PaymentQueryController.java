package com.developer.orderservice.payment.query.controller;

import com.developer.orderservice.client.UserServiceClient;
import com.developer.orderservice.common.exception.CustomException;
import com.developer.orderservice.common.exception.ErrorCode;
import com.developer.orderservice.payment.query.dto.ResponsePaymentDTO;
import com.developer.orderservice.payment.query.service.PaymentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment")
@Tag(name = "payment", description = "결제 API")
@RequiredArgsConstructor
public class PaymentQueryController {

    private final PaymentQueryService paymentQueryService;
    private final UserServiceClient userServiceClient;

    // 결제 코드로 조회
    @GetMapping("/{paymentCode}")
    @Operation(summary = "결제 상세 내역 조회", description = "결제 코드를 통해 결제 상세 내역을 조회합니다.")
    public ResponseEntity<ResponsePaymentDTO> findByPaymentCode(@PathVariable("paymentCode") Long paymentCode) {

        Long currentUserCode = userServiceClient.getCurrentUserCode();

        ResponsePaymentDTO byPaymentCode = paymentQueryService.findByPaymentCode(currentUserCode, paymentCode);

        return ResponseEntity.ok(byPaymentCode);
    }

    // 사용자 코드로 전체 내역 조회
    @GetMapping
    @Operation(summary = "결제 목록 조회", description = "사용자가 결제한 전체 목록을 조회합니다.")
    public ResponseEntity<?> findAll() {
        Long currentUserCode = userServiceClient.getCurrentUserCode();

        List<ResponsePaymentDTO> paymentsByUserCode =
                paymentQueryService.findPaymentsByUserCode(currentUserCode);

        return ResponseEntity.ok(paymentsByUserCode);
    }

    // 사용자 결제 상태에 따라 값들 가져오기
    @GetMapping("/{paymentStatus}")
    @Operation(summary = "결제 상태에 따른 목록 조회", description = "결제 상태에 따른 사용자의 결제 목록을 조회합니다.")
    public ResponseEntity<List<ResponsePaymentDTO>> findByPaymentStatus(@PathVariable("paymentStatus") String paymentStatus) {
        Long currentUserCode = userServiceClient.getCurrentUserCode();

        List<ResponsePaymentDTO> responsePaymentDTO;

        // 어떤값인지에 따라 서비스 호출 (OK, READY, CANCEL)
        switch (paymentStatus){
            case "OK" -> responsePaymentDTO = paymentQueryService.findCompPaymentsByUserCode(currentUserCode);
            case "READY" -> responsePaymentDTO = paymentQueryService.findReadyPaymentsByUserCode(currentUserCode);
            case "CANCEL" -> responsePaymentDTO = paymentQueryService.findCancelPaymentsByUserCode(currentUserCode);
            default -> throw new CustomException(ErrorCode.NOT_FOUND_PAYMENT_STATUS);
        }

        return ResponseEntity.ok(responsePaymentDTO);
    }
}
