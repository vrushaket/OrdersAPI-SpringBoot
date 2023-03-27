package com.order.management.food;

import com.order.management.food.api.request.FoodRequest;
import com.order.management.food.api.response.FoodResponse;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    static int foodId = 19;

    @Test
    public void getFoodsRequest_shouldReturnFoodsResponse(){
        //given
        String expectedFoodsResponse = """
                [{"id":1,"name":"Fries","price":80},{"id":4,"name":"Spring Roll","price":100},{"id":6,"name":"Burger","price":150},{"id":7,"name":"Pizza","price":200}]
                """;
        //when
        ResponseEntity<String> actualFoodsResponse = restTemplate.getForEntity("/foods", String.class);
        //then
        Assertions.assertThat(expectedFoodsResponse.trim()).isEqualTo(actualFoodsResponse.getBody());
    }

    @Test
    public void givenFoodId_toRetrieveSpecificFood_shouldReturnFoodResponse(){
        //given
        String expectedFoodsResponse = """
                {"id":1,"name":"Fries","price":80}
                """;
        //when
        ResponseEntity<String> actualFoodsResponse = restTemplate.getForEntity("/foods/1", String.class);
        //then
        Assertions.assertThat(expectedFoodsResponse.trim()).isEqualTo(actualFoodsResponse.getBody());
    }

    @Test
    public void givenFoodRequest_toAddNewFood_shouldAddNewFood() throws JSONException {
        //given
        FoodRequest foodRequest = new FoodRequest("Noodles",100);
        //when
        ResponseEntity<FoodResponse> actualFoodResponseEntity = restTemplate.postForEntity("/foods", foodRequest, FoodResponse.class);
        //then
        Assertions.assertThat(actualFoodResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<FoodResponse> actualFoodsResponse = restTemplate.getForEntity("/foods/"+actualFoodResponseEntity.getBody().getId(), FoodResponse.class);
        Assertions.assertThat(foodRequest.getName()).isEqualTo(actualFoodsResponse.getBody().getName());
    }

    @Test
    public void givenFoodId_toDeleteFood_shouldDeleteFood() throws JSONException {
        //given
        long foodId = 19;
        //when
        ResponseEntity<FoodResponse> actualFoodResponseEntity = restTemplate.exchange("/foods/"+foodId, HttpMethod.DELETE, HttpEntity.EMPTY, FoodResponse.class);
        //then
        Assertions.assertThat(actualFoodResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<FoodResponse> actualFoodsResponse = restTemplate.getForEntity("/foods/"+actualFoodResponseEntity.getBody().getId(), FoodResponse.class);
        Assertions.assertThat(actualFoodsResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenFoodIdAndFoodRequest_toUpdateFood_shouldUpdateFood(){
        //given
        long foodId = 18;
        FoodRequest foodRequest = new FoodRequest("Noodles",200);
        //when
        ResponseEntity<FoodResponse> actualFoodResponseEntity = restTemplate.exchange("/foods/"+foodId,HttpMethod.PUT,new HttpEntity<>(foodRequest),FoodResponse.class);
        //then
        Assertions.assertThat(actualFoodResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<FoodResponse> updatedFoodResponse = restTemplate.getForEntity("/foods/"+foodId, FoodResponse.class);
        Assertions.assertThat(updatedFoodResponse.getBody().getPrice()).isEqualTo(200);
    }
}
