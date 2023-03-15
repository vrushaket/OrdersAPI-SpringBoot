package com.order.management.order.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.order.management.food.domain.Food;
import lombok.Getter;
import java.sql.Date;
import java.util.List;

@Getter
public class OrderRequest {

    private long customerId;
    private long paymentId;
    private long deliveryId;
    private Date date;
    private long quantity;
    private List<Food> foods;

    @JsonCreator
    public OrderRequest(long customerId, long paymentId, long deliveryId, Date date, long quantity, List<Food> foods) {
        this.customerId = customerId;
        this.paymentId = paymentId;
        this.deliveryId = deliveryId;
        this.date = date;
        this.quantity = quantity;
        this.foods = foods;
    }
}
