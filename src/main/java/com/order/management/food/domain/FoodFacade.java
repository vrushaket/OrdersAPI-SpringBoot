package com.order.management.food.domain;

import com.order.management.food.api.request.FoodRequest;
import com.order.management.food.exception.FoodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodFacade  {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodFacade(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food addFood(FoodRequest foodRequest) {
        Food food = new Food(foodRequest.getName(),foodRequest.getPrice());
        return foodRepository.save(food);
    }

    public Food deleteFood(long foodId) {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (!optionalFood.isPresent()) throw new FoodNotFoundException("id : "+ foodId);
        foodRepository.deleteById(foodId);
        return optionalFood.get();
    }

    public Food updateFood(long foodId, FoodRequest foodRequest) {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (!optionalFood.isPresent())  throw new FoodNotFoundException("id : "+foodId);
        Food existingFood = optionalFood.get();
        existingFood.setName(foodRequest.getName());
        existingFood.setPrice(foodRequest.getPrice());
        return foodRepository.save(existingFood);
    }

    public Food retrieveSpecificFood(long foodId) {
        Optional<Food> food = foodRepository.findById(foodId);
        if(food.isEmpty()) throw new FoodNotFoundException("id : "+foodId );
        return food.get();
    }

    public List<Food> retrieveAllFood() {
        List<Food> allFood = foodRepository.findAll();
        if(allFood.isEmpty()) return null;
        return allFood;
    }
}
