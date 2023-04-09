package com.order.management.demo;

import org.springframework.stereotype.Component;

@Component("Bike")
public class Bike implements Vehicle {
    @Override
    public String getModel() {
        return "Moto";
    }
}