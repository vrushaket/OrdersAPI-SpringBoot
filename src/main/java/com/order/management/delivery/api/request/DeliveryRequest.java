package com.order.management.delivery.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class DeliveryRequest {

    private String status;

    @JsonCreator
    public DeliveryRequest(String status) {
        this.status = status;
    }
}
