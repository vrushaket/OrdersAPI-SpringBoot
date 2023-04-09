package com.order.management.demo;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Person {
    private String name;
    private Double number = 0.0;

    @Bean
    @Scope("prototype")
    public Person personSingleton(){
        return new Person();
    }
}



