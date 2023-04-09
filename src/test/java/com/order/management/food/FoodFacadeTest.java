package com.order.management.food;

import com.order.management.food.api.request.FoodRequest;
import com.order.management.food.domain.Food;
import com.order.management.food.domain.FoodFacade;
import com.order.management.food.exception.FoodNotFoundException;
import com.order.management.order.domain.Order;
import com.order.management.payment.domain.Payment;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodFacadeTest {

    @Autowired
    FoodFacade foodFacade;

    @Test(expected = FoodNotFoundException.class)
    public void givenUnknownFoodId_toRetrieveFood_shouldThrowFoodNotFoundException(){
        //given
        long foodId = 111l;
        //when
        foodFacade.retrieveSpecificFood(foodId);
    }

    @Test
    public void givenFoodId_toRetrieveFood_shouldReturnFoodResponse(){
        //given
        long foodId = 1l;
        Food expectedFoodResponse = new Food("Fries",80);
        expectedFoodResponse.setId(1l);
        //when
        Food foodResponse = foodFacade.retrieveSpecificFood(foodId);
        //then
        Assertions.assertThat(foodResponse.toString()).isEqualTo(expectedFoodResponse.toString());
    }

    @Test
    public void givenRequestToRetrieveAllFood_shouldReturnAllFoodsResponse() throws JSONException {
        //given
        Food food1 = new Food("Fries",80);
        food1.setId(1l);
        Food food4 = new Food("Spring Roll",100);
        food4.setId(4l);
        Food food6 = new Food("Burger",150);
        food6.setId(6l);
        Food food7 = new Food("Pizza",200);
        food7.setId(7l);

        List<Food> expectedFoodList = new ArrayList<>();
        expectedFoodList.add(food1);
        expectedFoodList.add(food4);
        expectedFoodList.add(food6);
        expectedFoodList.add(food7);
        //when
        List<Food> actualFoodList = foodFacade.retrieveAllFood();
        //then
        System.out.println(expectedFoodList);
        Assertions.assertThat(actualFoodList.toString()).isEqualTo(expectedFoodList.toString());
    }

    @Test
    public void givenFoodRequest_toAddNewFood_shouldAddNewFood(){
        //given
        FoodRequest foodRequest = new FoodRequest("Noodles",100);
        Food expectedFoodResponse = new Food("Noodles",100);
        //when
        Food actualFoodResponse = foodFacade.addFood(foodRequest);
        expectedFoodResponse.setId(actualFoodResponse.getId());
        //then
        Assertions.assertThat(expectedFoodResponse.toString()).isEqualTo(actualFoodResponse.toString());
    }

    @Test(expected = FoodNotFoundException.class)
    public void givenUnKnownFoodId_toDeleteFood_shouldThrowFoodNotFoundException(){
        //given
        long foodId = 1000;
        //when
        foodFacade.deleteFood(foodId);
    }

    @Test
    public void givenFoodRequest_toDeleteFood_shouldDeleteFood(){
        //given
        long foodId =7l;
        Food expectedFoodResponse = new Food("Pizza",200);
        expectedFoodResponse.setId(foodId);
        //when
        Food actualFoodResponse = foodFacade.deleteFood(foodId);
        //then
        Assertions.assertThat(expectedFoodResponse.toString()).isEqualTo(actualFoodResponse.toString());
    }

    @Test(expected = FoodNotFoundException.class)
    public void givenInvalidFoodId_toUpdateFood_shouldThrowFoodNotFoundException(){
        //given
        long foodId = 1000;
        FoodRequest foodRequest = new FoodRequest("Noodles",200);
        //when
        foodFacade.updateFood(foodId,foodRequest);
    }

    @Test
    public void givenFoodId_toUpdateFood_shouldUpdateFood(){
        //given
        long foodId = 7l;
        Food expectedFoodResponse = new Food("Hakka Noodles",250);
        expectedFoodResponse.setId(foodId);
        FoodRequest foodRequest = new FoodRequest("Hakka Noodles",250);
        //when
        Food actualFoodResponse = foodFacade.updateFood(foodId,foodRequest);
        //then
        Assertions.assertThat(expectedFoodResponse.toString()).isEqualTo(actualFoodResponse.toString());
    }
}
