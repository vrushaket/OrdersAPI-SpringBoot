package com.order.management.order.service;

import com.order.management.customer.api.response.CustomerResponse;
import com.order.management.delivery.api.response.DeliveryResponse;
import com.order.management.food.api.response.FoodResponse;
import com.order.management.food.domain.FoodFacade;
import com.order.management.food.domain.FoodRepository;
import com.order.management.customer.domain.Customer;
import com.order.management.delivery.domain.Delivery;
import com.order.management.food.domain.Food;
import com.order.management.order.api.request.OrderRequest;
import com.order.management.order.api.response.OrderResponse;
import com.order.management.order.domain.Order;
import com.order.management.order.domain.OrderFacade;
import com.order.management.order.domain.OrderRepository;
import com.order.management.payment.api.response.PaymentResponse;
import com.order.management.payment.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderFacade orderFacade;

//    @Autowired
//    FoodFacade foodFacade;

    public OrderResponse addOrder(OrderRequest orderRequest) {
        Order order = orderFacade.addOrder(orderRequest);
        return new OrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getPayment(),order.getCustomer(),order.getDelivery());
    }

    public OrderResponse deleteOrder(long orderId) {
        Order order = orderFacade.deleteOrder(orderId);
        return new OrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getPayment(),order.getCustomer(),order.getDelivery());
    }

    public OrderResponse updateOrder(long orderId, OrderRequest orderRequest) {
        Order order = orderFacade.deleteOrder(orderId);
        return new OrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getPayment(),order.getCustomer(),order.getDelivery());
    }

    public OrderResponse retrieveSpecificOrder(long orderId) {
        Order order = orderFacade.retrieveSpecificOrder(orderId);
        return new OrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getPayment(),order.getCustomer(),order.getDelivery());
    }

    public List<OrderResponse> retrieveAllOrder() {
        List<Order> orders = orderFacade.retrieveAllOrder();
        List<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order: orders){
            orderResponses.add(new OrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getPayment(),order.getCustomer(),order.getDelivery()));
        }
        return orderResponses;
    }


    public PaymentResponse retrieveOrderPayment(long orderId) {
        Payment payment = orderFacade.retrieveOrderPayment(orderId);
        return new PaymentResponse(payment.getId(),payment.getAmount(),payment.getMethod(),payment.getStatus(),payment.getDate());
    }

    public DeliveryResponse retrieveOrderDelivery(long orderId) {
        Delivery delivery = orderFacade.retrieveOrderDelivery(orderId);
        return new DeliveryResponse(delivery.getId(),delivery.getStatus());
    }
    public List<FoodResponse> retrieveOrderFood(long orderId) {
        List<Food> foods = orderFacade.retrieveOrderFood(orderId);
        List<FoodResponse> foodResponses = new ArrayList<>();
        for (Food food: foods) {
            foodResponses.add(new FoodResponse(food.getId(),food.getName(),food.getPrice()));
        }
        return foodResponses;

    }
    public List<CustomerResponse> retrieveOrderCustomer(long orderId) {
        List<Customer> customers = orderFacade.retrieveOrderCustomer(orderId);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer: customers) {
            customerResponses.add(new CustomerResponse(customer.getId(),customer.getName(),customer.getAddress(),customer.getPhone()));
        }
        return customerResponses;
    }

}
