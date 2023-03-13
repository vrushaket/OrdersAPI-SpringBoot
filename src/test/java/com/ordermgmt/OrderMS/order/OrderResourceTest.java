package com.ordermgmt.OrderMS.order;

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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class OrderResourceTest {

    private static String GENERIC_ORDER_URL = "http://localhost:8000/orders";
    private static String SPECIFIC_ORDER_URL = "http://localhost:8000/orders/14";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenOrderId_shouldReturn404() throws Exception {
        //given
        String UNKNOWN_ORDER_URL = "http://localhost:8000/orders/1001";
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(UNKNOWN_ORDER_URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void givenOrderId_shouldReturnOrderDetails() throws Exception {
        //given
        String SPECIFIC_ORDER_URL = "http://localhost:8000/orders/13";
        String expectedResponse = """
               {"id":13,"customerId":1,"paymentId":1,"deliveryId":1,"date":"2023-03-12","quantity":2,"payment":{"id":1,"customerId":1,"amount":500,"method":"UPI","status":"success","date":"2023-03-12"},"customer":{"id":1,"name":"Vrushaket","address":"Pune","phone":"9595068833"},"delivery":{"id":1,"status":"on the way"}}
               """;
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(SPECIFIC_ORDER_URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedResponse.trim());
    }

    @Test
    void methodShouldReturnListOfOrders() throws Exception {
        //given
        String expectedResponse = """
             [{"id":4,"customerId":2,"paymentId":2,"deliveryId":2,"date":"2023-03-13","quantity":2,"payment":{"id":2,"customerId":2,"amount":1500,"method":"Debit Card","status":"success","date":"2023-03-11"},"customer":{"id":2,"name":"Abhijeet","address":"Mumbai","phone":"9944561278"},"delivery":{"id":2,"status":"waiting"}},{"id":5,"customerId":3,"paymentId":4,"deliveryId":4,"date":"2023-03-13","quantity":4,"payment":{"id":4,"customerId":3,"amount":200,"method":"Cash","status":"success","date":"2023-03-12"},"customer":{"id":3,"name":"Chetan","address":"Jalgaon","phone":"7588612345"},"delivery":{"id":4,"status":"canceled"}},{"id":6,"customerId":1,"paymentId":3,"deliveryId":2,"date":"2023-03-13","quantity":1,"payment":{"id":3,"customerId":1,"amount":1200,"method":"Credit Card","status":"success","date":"2023-03-12"},"customer":{"id":1,"name":"Vrushaket","address":"Pune","phone":"9595068833"},"delivery":{"id":2,"status":"waiting"}},{"id":13,"customerId":1,"paymentId":1,"deliveryId":1,"date":"2023-03-12","quantity":2,"payment":{"id":1,"customerId":1,"amount":500,"method":"UPI","status":"success","date":"2023-03-12"},"customer":{"id":1,"name":"Vrushaket","address":"Pune","phone":"9595068833"},"delivery":{"id":1,"status":"on the way"}},{"id":14,"customerId":1,"paymentId":1,"deliveryId":1,"date":"2023-03-12","quantity":2,"payment":{"id":1,"customerId":1,"amount":500,"method":"UPI","status":"success","date":"2023-03-12"},"customer":{"id":1,"name":"Vrushaket","address":"Pune","phone":"9595068833"},"delivery":{"id":1,"status":"on the way"}},{"id":15,"customerId":1,"paymentId":1,"deliveryId":1,"date":"2023-03-12","quantity":2,"payment":{"id":1,"customerId":1,"amount":500,"method":"UPI","status":"success","date":"2023-03-12"},"customer":{"id":1,"name":"Vrushaket","address":"Pune","phone":"9595068833"},"delivery":{"id":1,"status":"on the way"}},{"id":16,"customerId":1,"paymentId":1,"deliveryId":1,"date":"2023-03-12","quantity":2,"payment":{"id":1,"customerId":1,"amount":500,"method":"UPI","status":"success","date":"2023-03-12"},"customer":{"id":1,"name":"Vrushaket","address":"Pune","phone":"9595068833"},"delivery":{"id":1,"status":"on the way"}}]
              """;
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(GENERIC_ORDER_URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        JSONAssert.assertEquals(expectedResponse,result.getResponse().getContentAsString(),false);
        //Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedResponse.trim());
    }

    @Test
    void givenOrder_shouldAddNewOrder() throws Exception {
        //given
        String inputOrder = """
                {
                    "customerId": 1,
                    "paymentId": 1,
                    "deliveryId": 1,
                    "date": "2023-03-12",
                    "quantity": 2,
                    "foods": [
                        {
                            "id": 6,
                            "name": "Burger",
                            "price": 150
                        },
                        {
                            "id": 7,
                            "name": "Pizza",
                            "price": 200
                        }
                    ]
                }
                """;
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(GENERIC_ORDER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputOrder)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(201);
    }

    @Test
    void givenOrderIdAndOrder_shouldUpdateOrderForGivenOrderId() throws Exception {
        //given
        String inputOrder = """
                {
                     "customerId": 1,
                     "paymentId": 1,
                     "deliveryId": 1,
                     "date": "2023-03-12",
                     "quantity":11,
                     "foods":[
                             {
                                 "id":6,
                                 "name": "Burger",
                                 "price": 150
                             },
                             {
                                 "id":7,
                                 "name": "Pizza",
                                 "price": 200
                             }
                         ]
                 }
                """;
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(SPECIFIC_ORDER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputOrder)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void givenOrderId_shouldDeleteOrder() throws Exception {
        //given

        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(SPECIFIC_ORDER_URL)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(204);
    }
}
