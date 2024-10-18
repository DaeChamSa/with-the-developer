package com.developer.payment.command.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestPayDTO {
    private String orderUid;
    private String itemName;
    private String buyerName;
    private int paymentPrice;
    private String buyerEmail;

    @Builder
    public RequestPayDTO(String orderUid, String itemName, String buyerName, int paymentPrice, String buyerEmail) {
        this.orderUid = orderUid;
        this.itemName = itemName;
        this.buyerName = buyerName;
        this.paymentPrice = paymentPrice;
        this.buyerEmail = buyerEmail;
    }
}
