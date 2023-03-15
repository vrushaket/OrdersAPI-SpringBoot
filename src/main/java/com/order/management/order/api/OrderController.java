package com.order.management.order.api;

import com.order.management.customer.api.response.CustomerResponse;
import com.order.management.customer.domain.Customer;
import com.order.management.delivery.api.response.DeliveryResponse;
import com.order.management.delivery.domain.Delivery;
import com.order.management.food.api.response.FoodResponse;
import com.order.management.food.domain.Food;
import com.order.management.order.api.request.OrderRequest;
import com.order.management.order.api.response.OrderResponse;
import com.order.management.order.domain.Order;
import com.order.management.order.service.OrderService;
import com.order.management.payment.api.response.PaymentResponse;
import com.order.management.payment.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    ResponseEntity<List<OrderResponse>> retrieveAllOrder() {
        List<OrderResponse> orderResponses = orderService.retrieveAllOrder();
        if(orderResponses.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{orderId}")
    ResponseEntity<OrderResponse> retrieveSpecificOrder(@PathVariable long orderId){
        OrderResponse orderResponse = orderService.retrieveSpecificOrder(orderId);
        if(orderResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OrderResponse> addOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.addOrder(orderRequest);
        return ResponseEntity.ok(orderResponse);
    }

    @DeleteMapping("/{orderId}")
    ResponseEntity<OrderResponse> deleteOrder(@PathVariable long orderId){
        OrderResponse orderResponse = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    @PutMapping("/{orderId}")
    ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequest, @PathVariable long orderId){
        OrderResponse orderResponse = orderService.updateOrder(orderId, orderRequest);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/{orderId}/payment")
    ResponseEntity<PaymentResponse> retrieveOrderPayments(@PathVariable long orderId){
        PaymentResponse paymentResponse = orderService.retrieveOrderPayment(orderId);
        if(paymentResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/{orderId}/delivery")
    ResponseEntity<DeliveryResponse> retrieveOrderDelivery(@PathVariable long orderId){
        DeliveryResponse deliveryResponse = orderService.retrieveOrderDelivery(orderId);
        if(deliveryResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(deliveryResponse);
    }

    @GetMapping("/{orderId}/food")
    ResponseEntity<List<FoodResponse>> retrieveOrderFood(@PathVariable long orderId){
        List<FoodResponse> foodResponses= orderService.retrieveOrderFood(orderId);
        if(foodResponses == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foodResponses);
    }

    @GetMapping("/{orderId}/customer")
    ResponseEntity<List<CustomerResponse>> retrieveOrderCustomer(@PathVariable long orderId){
        List<CustomerResponse> customerResponses = orderService.retrieveOrderCustomer(orderId);
        if(customerResponses == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customerResponses);
    }
}
