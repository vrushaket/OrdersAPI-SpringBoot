package com.order.management.payment.api.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.order.management.customer.domain.Customer;
import com.order.management.delivery.domain.Delivery;
import com.order.management.food.domain.Food;
import com.order.management.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

@Getter
public class PaymentOrderResponse {

    private long id;
    private Date date;
    private long quantity;
    private List<Food> foods;
    private Customer customer;
    private Delivery delivery;

    @JsonCreator
    public PaymentOrderResponse(long id, Date date, long quantity, List<Food> foods, Customer customer, Delivery delivery) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.foods = foods;
        this.customer = customer;
        this.delivery = delivery;
    }
}
