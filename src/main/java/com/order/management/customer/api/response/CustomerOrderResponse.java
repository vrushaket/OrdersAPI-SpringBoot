package com.order.management.customer.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.order.management.delivery.domain.Delivery;
import com.order.management.food.domain.Food;
import com.order.management.payment.domain.Payment;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

@Getter
public class CustomerOrderResponse {

    private long id;
    private Date date;
    private long quantity;
    private List<Food> foods;
    private Payment payment;
    private Delivery delivery;

    @JsonCreator
    public CustomerOrderResponse(long id, Date date, long quantity, List<Food> foods, Payment payment, Delivery delivery) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.foods = foods;
        this.payment = payment;
        this.delivery = delivery;
    }
}
