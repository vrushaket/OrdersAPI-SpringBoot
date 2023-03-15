package com.order.management.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.order.management.order.domain.Order;
import com.order.management.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@NoArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private String phone;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy="customer",fetch = FetchType.LAZY)
    private List<Payment> payments;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy="customer",fetch = FetchType.LAZY)
    private List<Order> orders;

    public Customer(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
