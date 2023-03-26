package com.order.management.food;

import com.order.management.food.api.request.FoodRequest;
import com.order.management.food.api.response.FoodResponse;
import com.order.management.food.domain.Food;
import com.order.management.food.domain.FoodFacade;
import com.order.management.food.service.FoodService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FoodServiceTest {

    @Mock
    private FoodFacade foodFacade;

    private FoodService foodService;

    @Before
    public void setUp() throws Exception{
        foodService = new FoodService(foodFacade);
    }

    @Test
    public void givenFoodCreateRequest_shouldAddNewFood(){
        //given
        FoodRequest foodRequest = new FoodRequest("Noodles",100);
        Food food = new Food("Noodles",100);
        FoodResponse expectedFoodResponse = new FoodResponse(0l,"Noodles",100);
        //when
        Mockito.when(foodFacade.addFood(foodRequest)).thenReturn(food);
        FoodResponse actualFoodResponse = foodService.addFood(foodRequest);
        //then
        Assertions.assertThat(expectedFoodResponse.toString()).isEqualTo(actualFoodResponse.toString());
    }

    @Test
    public void givenFoodId_toDeleteFood_shouldDeleteFood(){
        //given
        long foodIdToDelete = 1;
        Food food =  new Food("Noodles",100);
        food.setId(foodIdToDelete);
        FoodResponse expectedFoodResponse = new FoodResponse(1,"Noodles",100);
        //when
        Mockito.when(foodFacade.deleteFood(foodIdToDelete)).thenReturn(food);
        FoodResponse actualFoodResponse = foodService.deleteFood(foodIdToDelete);
        //then
        Assertions.assertThat(expectedFoodResponse.getId()).isEqualTo(actualFoodResponse.getId());
    }

    @Test
    public void givenFoodIdAndFoodRequest_toUpdateFood_shouldUpdateFood(){
        //given
        long foodIdToUpdate= 1;
        FoodRequest foodRequest = new FoodRequest("Noodles",100);
        Food food = new Food("Noodles",100);
        food.setId(foodIdToUpdate);
        FoodResponse expectedFoodResponse = new FoodResponse(1,"Noodles",100);
        //when
        Mockito.when(foodFacade.updateFood(foodIdToUpdate,foodRequest)).thenReturn(food);
        FoodResponse actualFoodResponse = foodService.updateFood(foodIdToUpdate,foodRequest);
        //then
        Assertions.assertThat(expectedFoodResponse.toString()).isEqualTo(actualFoodResponse.toString());
    }

    @Test
    public void givenFoodId_toRetrieveFoodRequest_shouldReturnFoodResponse(){
        //given
        long foodId = 1;
        Food food = new Food("Noodles",100);
        food.setId(foodId);
        FoodResponse expectedFoodResponse = new FoodResponse(1,"Noodles",100);
        BDDMockito.given(foodFacade.retrieveSpecificFood(foodId)).willReturn(food);
        //when
        FoodResponse actualFoodResponse = foodService.retrieveSpecificFood(foodId);
        //then
        Assertions.assertThat(expectedFoodResponse.toString()).isEqualTo(actualFoodResponse.toString());
    }

    @Test
    public void givenRequestToRetrieveAllFood_shouldReturnAllFoodResponse(){
        //given
        List<Food> foods = new ArrayList<>();
        foods.add(new Food("Noodles",100));
        List<FoodResponse> expectedFoodResponses = new ArrayList<>();
        for(Food food: foods){
            expectedFoodResponses.add(new FoodResponse(food.getId(),food.getName(),food.getPrice()));
        }
        //when
        Mockito.when(foodFacade.retrieveAllFood()).thenReturn(foods);
        List<FoodResponse> actualFoodResponses = foodService.retrieveAllFood();
        //then
        Assertions.assertThat(expectedFoodResponses.toString()).isEqualTo(actualFoodResponses.toString());
    }

}