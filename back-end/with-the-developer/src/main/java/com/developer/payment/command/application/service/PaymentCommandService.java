package com.developer.payment.command.application.service;

import com.developer.payment.command.application.dto.PaymentCallbackRequest;
import com.developer.payment.command.application.dto.RequestPayDTO;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.data.repository.query.Param;

public interface PaymentCommandService {
    // 결제 요청 데이터 조회
    RequestPayDTO findRequestDTO(@Param("orderUid") String orderUid);
    // 결제(콜백)
    IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request);
    // 결제 취소
    void cancelPayment(Long userCode,@Param("paymentUid") String paymentUid);
}
