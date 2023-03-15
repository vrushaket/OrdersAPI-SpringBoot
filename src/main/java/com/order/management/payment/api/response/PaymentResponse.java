package com.order.management.payment.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import java.sql.Date;

@Getter
public class PaymentResponse {

    private long id;
    private int amount;
    private String method;
    private String status;
    private Date date;

    @JsonCreator
    public PaymentResponse(long id, int amount, String method, String status, Date date) {
        this.id = id;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.date = date;
    }
}
