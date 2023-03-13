package com.ordermgmt.OrderMS.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ordermgmt.OrderMS.payment.Payment;
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
    @OneToMany(mappedBy="customer",cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Customer(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
