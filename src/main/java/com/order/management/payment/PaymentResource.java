package com.order.management.payment;

import com.order.management.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PaymentResource {

    @Autowired
    PaymentServiceImpl paymentService;

    @GetMapping("/payments")
    ResponseEntity<Object> retrieveAllPayment(){
        List<Payment> payments = paymentService.retrieveAllPayment();
        if(payments.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/payments/{paymentId}")
    ResponseEntity<Object> retrieveSpecificPayment(@PathVariable long paymentId){
        Payment payment = paymentService.retrieveSpecificPayment(paymentId);
        if(payment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/payments/{paymentId}/order")
    ResponseEntity<Object> retrievePaymentOrder(@PathVariable long paymentId){
        Order order = paymentService.retrievePaymentOrder(paymentId);
        if(order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    ResponseEntity<Object> addPayment(@RequestBody Payment payment){
        paymentService.addPayment(payment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{paymentId}").buildAndExpand(payment.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/payments/{paymentId}", method = RequestMethod.DELETE)
    ResponseEntity<Object> deletePayment(@PathVariable long paymentId){
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/payments/{paymentId}", method = RequestMethod.PUT)
    ResponseEntity<Object> updatePayment(@RequestBody Payment payment, @PathVariable long paymentId){
        paymentService.updatePayment(paymentId,payment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{paymentId}").buildAndExpand(payment.getId()).toUri();
        return ResponseEntity.ok(location);
    }


}
