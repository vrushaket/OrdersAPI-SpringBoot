package com.ordermgmt.OrderMS.delivery;

import com.ordermgmt.OrderMS.order.Order;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status;

    public Delivery(String status) {
        this.status = status;
    }
}
