package com.order.management.food.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class FoodResponse {

    private long id;
    private String name;
    private int price;

    @JsonIgnore
    public FoodResponse(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
