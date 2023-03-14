package com.order.management.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    FoodRepository repository;

    public void addFood(Food food) {
        repository.save(food);
    }

    public void deleteFood(long foodId) {
        Optional<Food> optionalFood = repository.findById(foodId);
        if (optionalFood.isPresent()) {
            repository.deleteById(foodId);
        }
    }

    public void updateFood(long foodId, Food food) {
        Optional<Food> optionalFood = repository.findById(foodId);
        if (optionalFood.isPresent()) {
            Food existingFood = optionalFood.get();
            existingFood.setName(food.getName());
            existingFood.setPrice(food.getPrice());
            repository.save(existingFood);
        }
    }

    public Food retrieveSpecificFood(long foodId) {
        Optional<Food> food = repository.findById(foodId);
        if(food.isEmpty()) return null;
        return food.get();
    }

    public List<Food> retrieveAllFood() {
        List<Food> allFood = repository.findAll();
        if(allFood.isEmpty()) return null;
        return allFood;
    }
}
