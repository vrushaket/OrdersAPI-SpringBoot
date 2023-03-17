package com.order.management.customer.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomerResponse {

    private long id;
    private String name;
    private String address;
    private String phone;

    @JsonCreator
    public CustomerResponse(long id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
