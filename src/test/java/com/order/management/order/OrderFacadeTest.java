package com.order.management.order;

import com.order.management.customer.domain.Customer;
import com.order.management.delivery.domain.Delivery;
import com.order.management.food.domain.Food;
import com.order.management.order.api.request.OrderRequest;
import com.order.management.order.domain.Order;
import com.order.management.order.domain.OrderFacade;
import com.order.management.order.exception.OrderNotFoundException;
import com.order.management.order.domain.Order;
import com.order.management.payment.domain.Payment;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.C;
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
@Transactional
public class OrderFacadeTest {

    @Autowired
    OrderFacade orderFacade;

    @Test(expected = OrderNotFoundException.class)
    public void givenUnknownOrderId_toRetrieveOrder_shouldThrowOrderNotFoundException(){
        //given
        long orderId = 111l;
        //when
        orderFacade.retrieveSpecificOrder(orderId);
    }

    @Test
    public void givenOrderId_toRetrieveOrder_shouldReturnOrderResponse(){
        //given
        long orderId = 4l;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        long dateInMillis = cal.getTimeInMillis();
        Order expectedOrderResponse = new Order(2,2,2,new Date(dateInMillis),2,null);
        expectedOrderResponse.setId(orderId);
        //when
        Order orderResponse = orderFacade.retrieveSpecificOrder(orderId);
        //then
        Assertions.assertThat(orderResponse.toString()).isEqualTo(expectedOrderResponse.toString());
    }

    @Test
    public void givenRequestToRetrieveAllOrder_shouldReturnAllOrdersResponse() throws JSONException {
        //given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        long dateInMillis = cal.getTimeInMillis();

        Order order4 = new Order(2,2,2,new Date(dateInMillis),2,null);
        order4.setId(4l);
        Order order5 = new Order(3,4,4,new Date(dateInMillis),4,null);
        order5.setId(5l);
        Order order6 =new Order(1,3,2,new Date(dateInMillis),1,null);
        order6.setId(6l);
        List<Order> expectedOrderList = new ArrayList<>();
        expectedOrderList.add(order4);
        expectedOrderList.add(order5);
        expectedOrderList.add(order6);
        //when
        List<Order> actualOrderList = orderFacade.retrieveAllOrder();
        //then
        System.out.println(expectedOrderList);
        Assertions.assertThat(actualOrderList.toString()).isEqualTo(expectedOrderList.toString());
    }

    @Test
    public void givenOrderRequest_toAddNewOrder_shouldAddNewOrder(){
        //given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        long dateInMillis = cal.getTimeInMillis();

        List<Food> foods = new ArrayList<>();
        Food food1 = new Food("Burger",150);
        food1.setId(6);
        Food food2 = new Food("Pizza",200);
        food2.setId(7);
        foods.add(food1);
        foods.add(food2);

        OrderRequest orderRequest = new OrderRequest(1,1,1,new Date(dateInMillis),2,foods);
        Order expectedOrderResponse = new Order(1,1,1,new Date(dateInMillis),2,foods);
        //when
        Order actualOrderResponse = orderFacade.addOrder(orderRequest);
        expectedOrderResponse.setId(actualOrderResponse.getId());
        //then
        Assertions.assertThat(expectedOrderResponse.toString()).isEqualTo(actualOrderResponse.toString());
    }

    @Test(expected = OrderNotFoundException.class)
    public void givenUnKnownOrderId_toDeleteOrder_shouldThrowOrderNotFoundException(){
        //given
        long orderId = 1000;
        //when
        orderFacade.deleteOrder(orderId);
    }

    @Test
    public void givenOrderRequest_toDeleteOrder_shouldDeleteOrder(){
        //given
        long orderId = 6;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        long dateInMillis = cal.getTimeInMillis();
        Order expectedOrderResponse = new Order(1,3,2,new Date(dateInMillis),1,null);
        expectedOrderResponse.setId(orderId);
        //when
        Order actualOrderResponse = orderFacade.deleteOrder(orderId);
        //then
        Assertions.assertThat(expectedOrderResponse.toString()).isEqualTo(actualOrderResponse.toString());
    }

    @Test(expected = OrderNotFoundException.class)
    public void givenInvalidOrderId_toUpdateOrder_shouldThrowOrderNotFoundException(){
        //given
        long orderId = 1000;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        long dateInMillis = cal.getTimeInMillis();

        List<Food> foods = new ArrayList<>();
        Food food1 = new Food("Burger",150);
        food1.setId(6);
        Food food2 = new Food("Pizza",200);
        food2.setId(7);
        foods.add(food1);
        foods.add(food2);
        OrderRequest orderRequest = new OrderRequest(1,1,1,new Date(dateInMillis),2,foods);
        //when
        orderFacade.updateOrder(orderId,orderRequest);
    }

    @Test
    public void givenOrderId_toUpdateOrder_shouldUpdateOrder(){
        //given
        long orderId = 6l;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        long dateInMillis = cal.getTimeInMillis();
        Order expectedOrderResponse = new Order(1,3,2,new Date(dateInMillis),1,null);
        expectedOrderResponse.setId(orderId);
        OrderRequest orderRequest = new OrderRequest(1,3,2,new Date(dateInMillis),1,null);
        //when
        Order actualOrderResponse = orderFacade.updateOrder(orderId,orderRequest);
        //then
        Assertions.assertThat(expectedOrderResponse.toString()).isEqualTo(actualOrderResponse.toString());
    }

    @Test(expected = OrderNotFoundException.class)
    public void givenUnknownOrderId_toRetrieveOrderPaymentDetails_shouldThrowOrderNotFoundException(){
        //given
        long orderId = 64l;
        //when
        orderFacade.retrieveOrderPayment(orderId);
    }

    @Test
    public void givenOrderId_toRetrieveOrderPaymentDetails_shouldReturnPaymentDetails(){
        //given
        long orderId = 6l;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 12);
        long dateInMillis = cal.getTimeInMillis();
        Payment expectedPaymentResponse = new Payment(1l,1200,"Credit Card","success",new Date(dateInMillis));
        //when
        Payment actualPaymentResponse = orderFacade.retrieveOrderPayment(orderId);
        expectedPaymentResponse.setId(actualPaymentResponse.getId());
        //then
        Assertions.assertThat(expectedPaymentResponse.toString()).isEqualTo(actualPaymentResponse.toString());
    }

    @Test(expected = OrderNotFoundException.class)
    public void givenUnknownOrderId_toRetrieveOrderCustomerDetails_shouldThrowOrderNotFoundException(){
        //given
        long orderId = 64l;
        //when
        orderFacade.retrieveOrderCustomer(orderId);
    }

    @Test
    public void givenOrderId_toRetrieveOrderCustomerDetails_shouldReturnCustomerDetails(){
        //given
        long orderId = 4l;
        Customer expectedCustomerResponse = new Customer("Abhijeet","Mumbai","9944561278");
        //when
        Customer actualCustomerResponse = orderFacade.retrieveOrderCustomer(orderId);
        expectedCustomerResponse.setId(actualCustomerResponse.getId());
        //then
        Assertions.assertThat(expectedCustomerResponse.toString()).isEqualTo(actualCustomerResponse.toString());
    }

    @Test(expected = OrderNotFoundException.class)
    public void givenUnknownOrderId_toRetrieveOrderFoodsDetails_shouldThrowOrderNotFoundException(){
        //given
        long orderId = 64l;
        //when
        orderFacade.retrieveOrderFood(orderId);
    }

    @Test
    public void givenOrderId_toRetrieveOrderFoods_shouldReturnCustomerDetails(){
        //given
        long orderId = 4l;
        List<Food> expectedFoodsResponse = new ArrayList<>();
        expectedFoodsResponse.add(new Food("Fries",80));

        //when
        List<Food> actualFoodResponse = orderFacade.retrieveOrderFood(orderId);
        expectedFoodsResponse.get(0).setId(actualFoodResponse.get(0).getId());
        //then
        Assertions.assertThat(expectedFoodsResponse.toString()).isEqualTo(actualFoodResponse.toString());
    }

    @Test(expected = OrderNotFoundException.class)
    public void givenUnknownOrderId_toRetrieveOrderDeliveryDetails_shouldThrowOrderNotFoundException(){
        //given
        long orderId = 64l;
        //when
        orderFacade.retrieveOrderDelivery(orderId);
    }

    @Test
    public void givenOrderId_toRetrieveOrderDeliveryDetails_shouldReturnDeliveryDetails(){
        //given
        long orderId = 4l;
        Delivery expectedDeliveryResponse = new Delivery("waiting");
        //when
        Delivery actualDeliveryResponse = orderFacade.retrieveOrderDelivery(orderId);
        expectedDeliveryResponse.setId(actualDeliveryResponse.getId());
        //then
        Assertions.assertThat(expectedDeliveryResponse.toString()).isEqualTo(actualDeliveryResponse.toString());
    }
}
