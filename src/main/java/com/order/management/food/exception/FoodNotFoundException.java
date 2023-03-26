package com.order.management.food.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class FoodNotFoundException extends RuntimeException{
    public FoodNotFoundException(String message){
        super(message);
    }
}
