package com.order.management.order.domain;

import com.order.management.food.domain.Food;
import com.order.management.order.domain.Order;
import jakarta.persistence.*;

@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private long orderId;
    @Column(name = "food_id")
    private long foodId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id",insertable=false, updatable=false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id",insertable=false, updatable=false)
    private Food food;
}
