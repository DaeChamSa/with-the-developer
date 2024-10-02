package com.developer.payment.query.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.payment.query.dto.ResponsePaymentDTO;
import com.developer.payment.query.service.PaymentQueryService;
import com.developer.user.security.SecurityUtil;
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

    // 결제 코드로 조회
    @GetMapping("/detail/{paymentCode}")
    public ResponseEntity<ResponsePaymentDTO> findByPaymentCode(@PathVariable("paymentCode") Long paymentCode) {

        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        ResponsePaymentDTO byPaymentCode = paymentQueryService.findByPaymentCode(currentUserCode, paymentCode);

        return ResponseEntity.ok(byPaymentCode);
    }

    // 사용자 코드로 전체 내역 조회
    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

        List<ResponsePaymentDTO> paymentsByUserCode =
                paymentQueryService.findPaymentsByUserCode(currentUserCode);

        return ResponseEntity.ok(paymentsByUserCode);
    }

    // 사용자 결제 상태에 따라 값들 가져오기
    @GetMapping("/list/{paymentStatus}")
    public ResponseEntity<List<ResponsePaymentDTO>> findByPaymentStatus(@PathVariable("paymentStatus") String paymentStatus) {
        Long currentUserCode = SecurityUtil.getCurrentUserCode();

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
