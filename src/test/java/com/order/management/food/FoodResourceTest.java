package com.order.management.food;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


//Integration Test
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class FoodResourceTest {

    private static String GENERIC_FOOD_URL = "http://localhost:8000/foods";
    private static String SPECIFIC_FOOD_URL = "http://localhost:8000/foods/2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FoodResource foodResource;

    @Test
    void methodShouldReturnListOfFood() throws Exception {
        //given
        String expectedResponse = """
               [{"id":1,"name":"Spring Roll","price":120},{"id":2,"name":"Aloo Roll","price":120},{"id":3,"name":"Spring Roll","price":120},{"id":4,"name":"Spring Roll","price":120},{"id":5,"name":"Spring Roll","price":120},{"id":6,"name":"Spring Roll","price":120},{"id":7,"name":"Spring Roll","price":120}]
               """;

        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                            .get(GENERIC_FOOD_URL)
                                            .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //then
        //Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedResponse.trim());
        JSONAssert.assertEquals(expectedResponse,result.getResponse().getContentAsString(),false);
    }

    @Test
    void givenFoodId_shouldReturn404() throws Exception {
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
    void givenFoodId_shouldReturnFoodDetails() throws Exception {
        //given
        String expectedResponse = """
                {
                    "id": 2,
                    "name": "Aloo Roll",
                    "price": 120
                }
                """;
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(SPECIFIC_FOOD_URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        //Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedResponse.trim());
        JSONAssert.assertEquals(expectedResponse,result.getResponse().getContentAsString(),true);
    }

    @Test
    void givenFood_shouldAddNewFood() throws Exception {
        //given
        String inputFood = """
                {"name":"Spring Roll","price":120}
                """;
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                            .post(GENERIC_FOOD_URL)
                                            .accept(MediaType.APPLICATION_JSON)
                                            .content(inputFood)
                                            .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(201);
    }

    @Test
    void givenFoodId_shouldDeleteFood() throws Exception {
        //given
        String SPECIFIC_FOOD_URL = "http://localhost:8000/foods/2";
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(SPECIFIC_FOOD_URL)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(204);
    }

    @Test
    void givenFoodIdAndFood_shouldUpdateFoodForGivenFoodId() throws Exception {
        //given
        String SPECIFIC_FOOD_URL = "http://localhost:8000/foods/4";
        String inputFood = """
                {
                    "name": "Fries",
                    "price": 50
                }
                """;
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(SPECIFIC_FOOD_URL)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputFood)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

}
