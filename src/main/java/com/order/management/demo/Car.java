package com.order.management.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("Car")
@Primary
public class Car implements Vehicle {
    @Override
    public String getModel() {
        return "BMW";
    }
}