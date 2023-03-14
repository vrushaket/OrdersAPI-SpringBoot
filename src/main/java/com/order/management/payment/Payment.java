package com.order.management.payment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.order.management.order.Order;
import com.order.management.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@NoArgsConstructor
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_id")
    private Long customerId;

    private int amount;
    private String method;
    private String status;
    private Date date;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="customer_id", insertable=false, updatable=false)
    private Customer customer;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "id")
    Order order;

    public Payment(Long customerId, int amount, String method, String status, Date date) {
        this.customerId = customerId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.date = date;
    }
}
