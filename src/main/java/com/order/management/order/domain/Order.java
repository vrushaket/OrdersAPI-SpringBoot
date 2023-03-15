package com.order.management.order.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.order.management.customer.domain.Customer;
import com.order.management.delivery.domain.Delivery;
import com.order.management.food.domain.Food;
import com.order.management.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "payment_id")
    private long paymentId;

    @Column(name = "delivery_id")
    private long deliveryId;

    private Date date;
    private long quantity;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "order_items",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "food_id") }
    )
    private List<Food> foods;

    //    @JsonIgnore
    @OneToOne(mappedBy = "order")
    @JoinColumn(name = "payment_id", insertable=false, updatable=false)
    private Payment payment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id", insertable=false, updatable=false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name="delivery_id", insertable=false, updatable=false)
    private Delivery delivery;

    public Order(long customerId, long paymentId, long deliveryId, Date date, long quantity, List<Food> foods) {
        this.customerId = customerId;
        this.paymentId = paymentId;
        this.deliveryId = deliveryId;
        this.date = date;
        this.quantity = quantity;
        this.foods = foods;
    }
}
