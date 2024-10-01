package com.developer.payment.query.mapper;

import com.developer.payment.query.dto.ResponsePaymentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    ResponsePaymentDTO findByPaymentCode(Long paymentCode);

}
