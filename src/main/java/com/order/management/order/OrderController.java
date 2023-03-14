package com.order.management.order;

import com.order.management.customer.domain.Customer;
import com.order.management.delivery.Delivery;
import com.order.management.food.Food;
import com.order.management.payment.Payment;
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
    OrderServiceImpl orderService;


    @GetMapping
    ResponseEntity<Object> retrieveAllOrder() {
        List<Order> orders = orderService.retrieveAllOrder();
        if(orders.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    ResponseEntity<Object> retrieveSpecificOrder(@PathVariable long orderId){
        Order order = orderService.retrieveSpecificOrder(orderId);
        if(order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> addOrder(@RequestBody Order order){
        orderService.addOrder(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderId}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/{orderId}")
    ResponseEntity<Object> deleteOrder(@PathVariable long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}")
    ResponseEntity<Object> updateOrder(@RequestBody Order order, @PathVariable long orderId){
        orderService.updateOrder(orderId,order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderId}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.ok(location);
    }

    @GetMapping("/{orderId}/payment")
    ResponseEntity<Object> retrieveOrderPayments(@PathVariable long orderId){
        Payment payment = orderService.retrieveOrderPayment(orderId);
        if(payment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payment);
    }
    @GetMapping("/{orderId}/delivery")
    ResponseEntity<Object> retrieveOrderDelivery(@PathVariable long orderId){
        Delivery delivery = orderService.retrieveOrderDelivery(orderId);
        if(delivery == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(delivery);
    }

    @GetMapping("/{orderId}/food")
    ResponseEntity<Object> retrieveOrderFood(@PathVariable long orderId){
        List<Food> foods = orderService.retrieveOrderFood(orderId);
        if(foods == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{orderId}/customer")
    ResponseEntity<Object> retrieveOrderCustomer(@PathVariable long orderId){
        List<Customer> customers = orderService.retrieveOrderCustomer(orderId);
        if(customers == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customers);
    }

}
