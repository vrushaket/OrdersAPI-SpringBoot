package com.order.management.order.api.response;

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
public class OrderResponse {

    private long id;
    private Date date;
    private long quantity;
    private List<Food> foods;
    private Payment payment;
    private Customer customer;
    private Delivery delivery;

    @JsonCreator
    public OrderResponse(long id, Date date, long quantity, List<Food> foods, Payment payment, Customer customer, Delivery delivery) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.foods = foods;
        this.payment = payment;
        this.customer = customer;
        this.delivery = delivery;
    }
}
