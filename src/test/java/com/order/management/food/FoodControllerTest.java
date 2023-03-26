package com.order.management.food;

import com.order.management.food.api.FoodController;
import com.order.management.food.api.request.FoodRequest;
import com.order.management.food.api.response.FoodResponse;
import com.order.management.food.service.FoodService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(FoodController.class)
public class FoodControllerTest {

    private static String GENERIC_FOOD_URL = "http://localhost:8000/foods";
    private static String SPECIFIC_FOOD_URL = "http://localhost:8000/foods/2";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodService foodService;

    @Test
    public void methodShouldReturnListOfFood() throws Exception {
        //given
        String expectedResponse = """
               [{"id":2,"name":"Aloo Roll","price":120}]
              """;
        List<FoodResponse> foodResponseList = new ArrayList<>();
        foodResponseList.add(new FoodResponse(2l,"Aloo Roll",120));

        given(foodService.retrieveAllFood()).willReturn(foodResponseList);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                            .get(GENERIC_FOOD_URL)
                                            .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedResponse.trim());
    }

    @Test
    public void givenFoodId_shouldReturn404() throws Exception {
        //given
        String UNKNOWN_FOOD_URL = "http://localhost:8000/foods/1001";
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(UNKNOWN_FOOD_URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    public void givenFoodId_shouldReturnFoodDetails() throws Exception {
        //given
        long foodId = 2l;
        String expectedResponse = """
               {"id":2,"name":"Aloo Roll","price":120}
                """;
        FoodResponse foodResponse = new FoodResponse(2l,"Aloo Roll",120);
        given(foodService.retrieveSpecificFood(foodId)).willReturn(foodResponse);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(SPECIFIC_FOOD_URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(expectedResponse.trim()).isEqualTo(result.getResponse().getContentAsString());
    }

    @Test
    public void givenFood_shouldAddNewFood() throws Exception {
        //given
        String inputFood = """
                {"name":"Noodles","price":100}
                """;
        String expectedFoodResponse = """
                {"id":7,"name":"Noodles","price":100}
                """;
        FoodRequest foodRequest = new FoodRequest("Noodles",100);
        FoodResponse foodResponse = new FoodResponse(7l,"Noodles",100);
        given(foodService.addFood(foodRequest)).willReturn(foodResponse);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                            .post(GENERIC_FOOD_URL)
                                            .accept(MediaType.APPLICATION_JSON)
                                            .content(inputFood)
                                            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
//        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(201);
        Assertions.assertThat(expectedFoodResponse.trim()).isEqualTo(result.getResponse().getContentAsString());
    }

    @Test
    public void givenFoodId_shouldDeleteFood() throws Exception {
        //given
        String SPECIFIC_FOOD_URL = "http://localhost:8000/foods/4";
        long foodId = 4l;
        String expectedResponse = """
                {"id":4,"name":"Fries","price":50}
                """;
        FoodResponse foodResponse = new FoodResponse(4l,"Fries",50);
        given(foodService.deleteFood(foodId)).willReturn(foodResponse);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(SPECIFIC_FOOD_URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(expectedResponse.trim()).isEqualTo(result.getResponse().getContentAsString());
    }

    @Test
    public void givenFoodIdAndFood_shouldUpdateFoodForGivenFoodId() throws Exception {
        //given
        String SPECIFIC_FOOD_URL = "http://localhost:8000/foods/4";
        long foodId = 4l;
        String inputFood = """
                {"id":4,"name":"French Fries","price":50}
                """;
        String expectedFoodResponse = """
                {"id":4,"name":"French Fries","price":50}
                """;
        FoodResponse foodResponse = new FoodResponse(4l,"French Fries",50);
        FoodRequest foodRequest = new FoodRequest("French Fries",50);
        given(foodService.updateFood(foodId,foodRequest)).willReturn(foodResponse);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(SPECIFIC_FOOD_URL)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputFood)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        System.out.println(result.getResponse().getContentAsString());
        Assertions.assertThat(expectedFoodResponse.trim()).isEqualTo(result.getResponse().getContentAsString());
    }
}
