package com.order.management.food.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FoodResponse {

    private long id;
    private String name;
    private int price;

    @JsonCreator
    public FoodResponse(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("price") int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
