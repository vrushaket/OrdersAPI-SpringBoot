package com.ordermgmt.OrderMS.order;

import com.ordermgmt.OrderMS.food.Food;
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
