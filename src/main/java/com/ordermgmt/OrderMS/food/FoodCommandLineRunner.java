package com.ordermgmt.OrderMS.food;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class FoodCommandLineRunner implements CommandLineRunner {

    @Autowired
    FoodServiceImpl foodService;

    @Override
    public void run(String... args) throws Exception {
//        Food food1 = new Food("Coke",50);
//        Food food2 = new Food("Burger",90);
//        Food food3 = new Food("Pizza",300);
//        Food food4 = new Food("Noodles",100);
//        Food food5 = new Food("Fries",80);
//
//        foodService.addFood(food1);
//        foodService.addFood(food2);
//        foodService.addFood(food3);
//        foodService.addFood(food4);
//        foodService.addFood(food5);
    }
}
