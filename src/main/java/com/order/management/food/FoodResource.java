package com.order.management.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class FoodResource {

    @Autowired
    FoodServiceImpl foodService;

    @GetMapping("/foods")
    ResponseEntity<Object> retrieveAllFood(){
        List<Food> foods = foodService.retrieveAllFood();
        if(foods.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/foods/{foodId}")
    ResponseEntity<Object> retrieveSpecificFood(@PathVariable long foodId){
        Food food = foodService.retrieveSpecificFood(foodId);
        if(food == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(food);
    }

    @RequestMapping(value = "/foods", method = RequestMethod.POST)
    ResponseEntity<Object> addFood(@RequestBody Food food){
        foodService.addFood(food);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{foodId}").buildAndExpand(food.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/foods/{foodId}", method = RequestMethod.DELETE)
    ResponseEntity<Object> deleteFood(@PathVariable long foodId){
        foodService.deleteFood(foodId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/foods/{foodId}", method = RequestMethod.PUT)
    ResponseEntity<Object> updateFood(@RequestBody Food food,@PathVariable long foodId){
        foodService.updateFood(foodId,food);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{foodId}").buildAndExpand(food.getId()).toUri();
        return ResponseEntity.ok(location);
    }
}
