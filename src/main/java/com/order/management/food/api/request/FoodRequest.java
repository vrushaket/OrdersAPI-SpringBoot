package com.order.management.food.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class FoodRequest {
    private String name;
    private int price;

    @JsonCreator
    public FoodRequest(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "FoodRequest{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
