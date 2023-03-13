package com.ordermgmt.OrderMS.food;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ordermgmt.OrderMS.order.Order;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int price;

    @JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "foods")
    private List<Order> orders;

    public Food(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
