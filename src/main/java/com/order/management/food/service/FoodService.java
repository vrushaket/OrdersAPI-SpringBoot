package com.order.management.food.service;

import com.order.management.food.api.request.FoodRequest;
import com.order.management.food.api.response.FoodResponse;
import com.order.management.food.domain.Food;
import com.order.management.food.domain.FoodFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService  {

    @Autowired
    FoodFacade foodFacade;

    public FoodResponse addFood(FoodRequest foodRequest) {
        Food food = foodFacade.addFood(foodRequest);
        return new FoodResponse(food.getId(),food.getName(),food.getPrice());
    }

    public FoodResponse deleteFood(long foodId) {
        Food food = foodFacade.deleteFood(foodId);
        return new FoodResponse(food.getId(),food.getName(),food.getPrice());
    }

    public FoodResponse updateFood(long foodId, FoodRequest foodRequest) {
        Food food = foodFacade.updateFood(foodId,foodRequest);
        return new FoodResponse(food.getId(),food.getName(),food.getPrice());
    }

    public FoodResponse retrieveSpecificFood(long foodId) {
        Food food = foodFacade.retrieveSpecificFood(foodId);
        return new FoodResponse(food.getId(),food.getName(),food.getPrice());
    }

    public List<FoodResponse> retrieveAllFood() {
        List<Food> foods = foodFacade.retrieveAllFood();
        List<FoodResponse> foodResponses = new ArrayList<>();
        for (Food food: foods) {
            foodResponses.add(new FoodResponse(food.getId(),food.getName(),food.getPrice()));
        }
        return foodResponses;
    }
}
