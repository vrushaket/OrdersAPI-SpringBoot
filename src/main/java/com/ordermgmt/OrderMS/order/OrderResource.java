package com.ordermgmt.OrderMS.order;

import com.ordermgmt.OrderMS.customer.Customer;
import com.ordermgmt.OrderMS.delivery.Delivery;
import com.ordermgmt.OrderMS.food.Food;
import com.ordermgmt.OrderMS.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class OrderResource {

    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/orders")
    ResponseEntity<Object> retrieveAllOrder(){
        List<Order> orders = orderService.retrieveAllOrder();
        if(orders.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{orderId}")
    ResponseEntity<Object> retrieveSpecificOrder(@PathVariable long orderId){
        Order order = orderService.retrieveSpecificOrder(orderId);
        if(order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    ResponseEntity<Object> addOrder(@RequestBody Order order){
        orderService.addOrder(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderId}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.DELETE)
    ResponseEntity<Object> deleteOrder(@PathVariable long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.PUT)
    ResponseEntity<Object> updateOrder(@RequestBody Order order, @PathVariable long orderId){
        orderService.updateOrder(orderId,order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderId}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.ok(location);
    }

    @GetMapping("/orders/{orderId}/payment")
    ResponseEntity<Object> retrieveOrderPayments(@PathVariable long orderId){
        Payment payment = orderService.retrieveOrderPayment(orderId);
        if(payment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payment);
    }
    @GetMapping("/orders/{orderId}/delivery")
    ResponseEntity<Object> retrieveOrderDelivery(@PathVariable long orderId){
        Delivery delivery = orderService.retrieveOrderDelivery(orderId);
        if(delivery == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(delivery);
    }

    @GetMapping("/orders/{orderId}/food")
    ResponseEntity<Object> retrieveOrderFood(@PathVariable long orderId){
        List<Food> foods = orderService.retrieveOrderFood(orderId);
        if(foods == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/orders/{orderId}/customer")
    ResponseEntity<Object> retrieveOrderCustomer(@PathVariable long orderId){
        List<Customer> customers = orderService.retrieveOrderCustomer(orderId);
        if(customers == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customers);
    }

}
