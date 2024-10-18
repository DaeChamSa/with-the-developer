package com.developer.payment.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.payment.query.dto.ResponsePaymentDTO;
import com.developer.payment.query.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentQueryService {

    private final PaymentMapper paymentMapper;

    // 결제코드로 결제 내역 가져오기
    public ResponsePaymentDTO findByPaymentCode(Long userCode, Long paymentCode){


        ResponsePaymentDTO responsePaymentDTO =
                paymentMapper.findByPaymentCode(paymentCode);

        if (!Objects.equals(userCode, responsePaymentDTO.getUserCode())){

            // 결제 유저코드와 로그인된 유저코드가 다르다면
            throw new CustomException(ErrorCode.PAYMENT_NOT_MATCH_USER);
        }

        return responsePaymentDTO;
    }

    // 사용자 결제내역 가져오기
    public List<ResponsePaymentDTO> findPaymentsByUserCode(Long userCode){

        List<ResponsePaymentDTO> paymentsByUserCode =
                paymentMapper.findPaymentsByUserCode(userCode);

        return paymentsByUserCode;
    }


    // 사용자 결제완료 값들 가져오기
    public List<ResponsePaymentDTO> findCompPaymentsByUserCode(Long userCode){

        List<ResponsePaymentDTO>  paymentsByUserCode =
                paymentMapper.findCompPaymentsByUserCode(userCode);

        return paymentsByUserCode;
    }

    // 사용자 결제 취소 내역 가져오기
    public List<ResponsePaymentDTO> findCancelPaymentsByUserCode(Long userCode){

        List<ResponsePaymentDTO> paymentsByUserCode =
                paymentMapper.findCancelPaymentsByUserCode(userCode);

        return paymentsByUserCode;
    }

    // 사용자 미결제 내역들 가져오기
    public List<ResponsePaymentDTO> findReadyPaymentsByUserCode(Long userCode){

        List<ResponsePaymentDTO> paymentsByUserCode =
                paymentMapper.findReadyPaymentsByUserCode(userCode);

        return paymentsByUserCode;
    }
}
