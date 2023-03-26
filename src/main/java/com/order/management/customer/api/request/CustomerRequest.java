package com.order.management.customer.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class CustomerRequest {

    private final String name;
    private final String address;
    private final String phone;

    @JsonCreator
    public CustomerRequest(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
