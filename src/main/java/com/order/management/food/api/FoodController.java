package com.order.management.food.api;

import com.order.management.food.api.request.FoodRequest;
import com.order.management.food.api.response.FoodResponse;
import com.order.management.food.domain.Food;
import com.order.management.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    FoodService foodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<FoodResponse>> retrieveAllFood(){
        List<FoodResponse> foodResponses = foodService.retrieveAllFood();
        if(foodResponses.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foodResponses);
    }

    @GetMapping(value = "/{foodId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FoodResponse> retrieveSpecificFood(@PathVariable long foodId){
        FoodResponse foodResponse = foodService.retrieveSpecificFood(foodId);
        if(foodResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foodResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FoodResponse> addFood(@RequestBody FoodRequest foodRequest){
        FoodResponse foodResponse = foodService.addFood(foodRequest);
        return ResponseEntity.ok(foodResponse);
    }

    @DeleteMapping(value = "/{foodId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FoodResponse> deleteFood(@PathVariable long foodId){
        FoodResponse foodResponse = foodService.deleteFood(foodId);
        return ResponseEntity.ok(foodResponse);
    }

    @PutMapping(value = "/{foodId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FoodResponse> updateFood(@PathVariable long foodId, @RequestBody FoodRequest foodRequest){
        FoodResponse foodResponse = foodService.updateFood(foodId, foodRequest);
        return ResponseEntity.ok(foodResponse);
    }
}
