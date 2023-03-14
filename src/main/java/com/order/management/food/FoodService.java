package com.order.management.food;

import java.util.List;

public interface FoodService {
    void addFood(Food food);
    void deleteFood(long foodId);
    void updateFood(long foodId, Food food);
    Food retrieveSpecificFood(long foodId);
    List<Food> retrieveAllFood();
}
