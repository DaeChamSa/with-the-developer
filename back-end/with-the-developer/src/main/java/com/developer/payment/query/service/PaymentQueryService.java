package com.developer.payment.query.service;

import com.developer.order.query.mapper.OrderMapper;
import com.developer.payment.query.dto.ResponsePaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentQueryService {

    private final OrderMapper orderMapper;

    public List<ResponsePaymentDTO> findPaymentsByUserCode(Long userCode){

        return null;
    }
}
