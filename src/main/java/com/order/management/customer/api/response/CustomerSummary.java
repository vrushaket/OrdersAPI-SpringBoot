package com.order.management.customer.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomerSummary {

    private final long id;

    @JsonCreator
    public CustomerSummary(long id) {
        this.id = id;
    }
}
