package com.order.management.order;
import com.order.management.customer.domain.Customer;
import com.order.management.delivery.domain.Delivery;
import com.order.management.food.domain.Food;
import com.order.management.order.api.OrderController;
import com.order.management.order.api.request.OrderRequest;
import com.order.management.order.api.response.OrderResponse;
import com.order.management.order.exception.OrderNotFoundException;
import com.order.management.order.service.OrderService;
import com.order.management.payment.domain.Payment;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    private static String GENERIC_ORDERS_URL = "http://localhost:8000/orders";
    private static String SPECIFIC_ORDER_URL = "http://localhost:8000/orders/4";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void givenUnknownOrderId_willThrowNotFoundStatus() throws Exception {
        //given
        given(orderService.retrieveSpecificOrder(anyLong())).willThrow(new OrderNotFoundException());
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8000/orders/411")).andExpect(status().isNotFound());
    }
    @Test
    void givenOrderId_toRetrieveOrder_shouldReturnOrderDetails() throws Exception {
        //given
        long orderId = 4;
        String expectedResponse = """
               {"id":0,"date":"3923-04-13","quantity":1,"foods":[{"id":0,"name":"Fries","price":80}],"payment":{"id":0,"customerId":3,"amount":200,"method":"Cash","status":"success","date":"3923-04-12"},"customer":{"id":0,"name":"Abhijeet","address":"Mumbai","phone":"9944561278"},"delivery":{"id":0,"status":"waiting"}}
                """;
        List<Food> foods = new ArrayList<>();
        foods.add(new Food("Fries",80));
        Payment payment = new Payment(3l,200,"Cash","success",new Date(2023,03,12));
        Delivery delivery = new Delivery("waiting");
        Customer customer= new Customer("Abhijeet","Mumbai","9944561278");
        OrderResponse orderResponse = new OrderResponse(0l, new Date(2023,03,13),1,foods,payment,customer,delivery);
        given(orderService.retrieveSpecificOrder(orderId)).willReturn(orderResponse);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(SPECIFIC_ORDER_URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedResponse.trim());
    }

    @Test
    void givenRequestToRetrieveOrders_shouldReturnOrdersResponse() throws Exception {
        String expectedOrderResponse = """
                [{"id":0,"date":"3923-04-12","quantity":1,"foods":[{"id":0,"name":"Fries","price":80}],"payment":{"id":0,"customerId":5,"amount":2200,"method":"UPI","status":"success","date":"3923-04-12"},"customer":{"id":0,"name":"Vrushaket","address":"Pune","phone":"9595068833"},"delivery":{"id":0,"status":"waiting"}}]
                """;
        List<Food> foods = new ArrayList<>();
        foods.add(new Food("Fries",80));
        Payment payment = new Payment(5l,2200,"UPI","success",new Date(2023,03,12));
        Delivery delivery = new Delivery("waiting");
        List<OrderResponse> orderResponses = new ArrayList<>();
        Customer customer= new Customer("Vrushaket","Pune","9595068833");
        orderResponses.add(new OrderResponse(0l,new Date(2023,03,12),1,foods,payment,customer,delivery));
        given(orderService.retrieveAllOrder()).willReturn(orderResponses);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(GENERIC_ORDERS_URL))
                .andExpect(status().isOk())
                .andReturn();
        //then
        System.out.println(mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(expectedOrderResponse.trim()).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void givenOrderRequest_toAddNewOrder_shouldAddNewOrder() throws Exception {
        //given
        String inputOrder = """
                {"date":"2023-03-13","quantity":2,"foods":[{"id":1,"name":"Fries","price":80}],"payment":{"id":4,"customerId":3,"amount":200,"method":"Cash","status":"success","date":"2023-03-12"},"customer":{"id":2,"name":"Abhijeet","address":"Mumbai","phone":"9944561278"},"delivery":{"id":2,"status":"waiting"}}
                """;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 12);
        long dateInMillis = cal.getTimeInMillis();
        List<Food> foods = new ArrayList<>();
        foods.add(new Food("Burger",150));
        foods.add(new Food("Pizza",200));
        Payment payment = new Payment(3l,200,"Cash","success",new Date(2023,03,12));
        Delivery delivery = new Delivery("waiting");
        Customer customer= new Customer("Abhijeet","Mumbai","9944561278");
        OrderRequest orderRequest = new OrderRequest(1,1,1,new Date(dateInMillis),2,foods);
        OrderResponse expectedOrderResponse = new OrderResponse(0,new Date(dateInMillis),2,foods,payment,customer,delivery);
        given(orderService.addOrder(orderRequest)).willReturn(expectedOrderResponse);
        //when

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                                    .post(GENERIC_ORDERS_URL)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(inputOrder)
                                    .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();
        //then
        Assertions.assertThat(expectedOrderResponse.toString()).isEqualTo(result.getResponse().getContentAsString());

    }

    @Test
    void givenOrderIdAndOrder_shouldUpdateOrderForGivenOrderId() throws Exception {
        //given
        long orderId = 5;
        String expectedOrderResponse = """
                {"id":0,"date":"3923-04-13","quantity":1,"foods":[{"id":0,"name":"Fries","price":80}],"payment":{"id":0,"customerId":3,"amount":200,"method":"Cash","status":"success","date":"3923-04-12"},"customer":{"id":0,"name":"Abhijeet","address":"Mumbai","phone":"9944561278"},"delivery":{"id":0,"status":"waiting"}}
                """;

        List<Food> foods = new ArrayList<>();
        foods.add(new Food("Fries",80));
        Payment payment = new Payment(3l,200,"Cash","success",new Date(2023,03,12));
        Delivery delivery = new Delivery("waiting");
        Customer customer= new Customer("Abhijeet","Mumbai","9944561278");
        OrderRequest orderRequest = new OrderRequest(3l,0l,0l,new Date(2023,03,12),1,foods);
        OrderResponse orderResponse = new OrderResponse(0l, new Date(2023,03,13),1,foods,payment,customer,delivery);
        given(orderService.updateOrder(orderId,orderRequest)).willReturn(orderResponse);

        //when  
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(SPECIFIC_ORDER_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedOrderResponse))
                .andExpect(status().isOk())
                .andReturn();
        //then
        System.out.println("res: "+mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(expectedOrderResponse.trim()).isEqualTo(mvcResult.getResponse().getContentAsString());
    }
    @Test
    void givenOrderId_toDeleteOrder_shouldDeleteOrder() throws Exception {
        //given
        long orderId = 4;
        String expectedResponse = """
               {"id":0,"date":"3923-04-13","quantity":1,"foods":[{"id":0,"name":"Fries","price":80}],"payment":{"id":0,"customerId":3,"amount":200,"method":"Cash","status":"success","date":"3923-04-12"},"customer":{"id":0,"name":"Abhijeet","address":"Mumbai","phone":"9944561278"},"delivery":{"id":0,"status":"waiting"}}
                """;
        List<Food> foods = new ArrayList<>();
        foods.add(new Food("Fries",80));
        Payment payment = new Payment(3l,200,"Cash","success",new Date(2023,03,12));
        Delivery delivery = new Delivery("waiting");
        Customer customer= new Customer("Abhijeet","Mumbai","9944561278");
        OrderResponse orderResponse = new OrderResponse(0l, new Date(2023,03,13),1,foods,payment,customer,delivery);
        given(orderService.deleteOrder(orderId)).willReturn(orderResponse);
        //when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(SPECIFIC_ORDER_URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //then
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedResponse.trim());
    }
}
