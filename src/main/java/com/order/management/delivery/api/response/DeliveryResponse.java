package com.order.management.delivery.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class DeliveryResponse {

    private long id;
    private String status;

    @JsonCreator
    public DeliveryResponse(long id, String status) {
        this.id = id;
        this.status = status;
    }
}
