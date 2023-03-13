package com.ordermgmt.OrderMS.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ordermgmt.OrderMS.customer.Customer;
import com.ordermgmt.OrderMS.delivery.Delivery;
import com.ordermgmt.OrderMS.food.Food;
import com.ordermgmt.OrderMS.payment.Payment;
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
//    @JsonIgnore
    @JoinTable(
            name = "order_items",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "food_id") }
    )
    @JsonBackReference
    private List<Food> foods;

    @OneToOne
//    @JsonIgnore
    @JoinColumn(name = "payment_id", insertable=false, updatable=false)
    private Payment payment;

    @OneToOne
//    @JsonIgnore
    @JoinColumn(name = "customer_id", insertable=false, updatable=false)
    private Customer customer;

    @OneToOne
//    @JsonIgnore
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
