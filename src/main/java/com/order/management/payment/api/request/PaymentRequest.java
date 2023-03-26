package com.order.management.payment.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.sql.Date;

@Getter
public class PaymentRequest {

    private Long customerId;
    private int amount;
    private String method;
    private String status;
    private Date date;

    @JsonCreator
    public PaymentRequest(Long customerId, int amount, String method, String status, Date date) {
        this.customerId = customerId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.date = date;
    }
}
