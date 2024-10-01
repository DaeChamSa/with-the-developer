package com.developer.order.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.order.query.dto.OrderGoodsListDTO;
import com.developer.order.query.dto.OrderListDTO;
import com.developer.order.query.dto.ResponseOrderListDTO;
import com.developer.order.query.mapper.OrderMapper;
import com.developer.payment.query.dto.ResponsePaymentDTO;
import com.developer.payment.query.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;

    // 주문 내역 조회
    public List<ResponseOrderListDTO> orderList(Long userCode){

        // 사용자 코드로 주문 내역 찾기
        List<OrderListDTO> orderByUserCode = orderMapper.findOrderByUserCode(userCode);

        // 주문 내역이 없으면
        if (orderByUserCode.isEmpty()){

            throw new CustomException(ErrorCode.NOT_FOUND_ORDER_LIST);
        }

        List<ResponseOrderListDTO> responseOrderListDTOS = new ArrayList<>();

        for (OrderListDTO orderList : orderByUserCode) {
            
            // 주문 코드로 주문굿즈들 찾기
            List<OrderGoodsListDTO> orderGoodsByOrderCode =
                    orderMapper.findOrderGoodsByOrderCode(orderList.getOrderCode());

            // 결제 관련도 가져오기
            ResponsePaymentDTO paymentByOrderCode =
                    paymentMapper.findByPaymentCode(orderList.getPaymentCode());

            // responseOrderListDTO 생성
            ResponseOrderListDTO responseOrderListDTO = new ResponseOrderListDTO(
                    orderList.getOrderCode(),
                    orderList.getOrderDate(),
                    orderList.getOrderStatus(),
                    paymentByOrderCode,
                    orderGoodsByOrderCode
            );
            
            // 반환할 리스트에 넣기
            responseOrderListDTOS.add(responseOrderListDTO);
        }

        return responseOrderListDTOS;
    }

}
