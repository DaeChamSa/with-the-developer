package com.developer.payment.query.mapper;

import com.developer.payment.query.dto.ResponsePaymentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    // 결제번호로 결제 내역 가져오기
    ResponsePaymentDTO findByPaymentCode(Long paymentCode);

    // 사용자 결제내역 가져오기
    List<ResponsePaymentDTO> findPaymentsByUserCode(Long userCode);

    // 사용자 결제완료 값들 가져오기
    List<ResponsePaymentDTO> findCompPaymentsByUserCode(Long userCode);

    // 사용자 결제 취소 내역 가져오기
    List<ResponsePaymentDTO> findCancelPaymentsByUserCode(Long userCode);

    // 사용자 미결제 내역들 가져오기
    List<ResponsePaymentDTO> findReadyPaymentsByUserCode(Long userCode);
}
