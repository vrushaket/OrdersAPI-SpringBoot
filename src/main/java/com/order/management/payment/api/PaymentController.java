package com.order.management.payment.api;

import com.order.management.customer.domain.Customer;
import com.order.management.payment.api.request.PaymentRequest;
import com.order.management.payment.api.response.PaymentOrderResponse;
import com.order.management.payment.api.response.PaymentResponse;
import com.order.management.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<PaymentResponse>> retrieveAllPayment(){
        List<PaymentResponse> paymentResponses = paymentService.retrieveAllPayment();
        if(paymentResponses.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(paymentResponses);
    }

    @GetMapping(value = "/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PaymentResponse> retrieveSpecificPayment(@PathVariable long paymentId){
        PaymentResponse paymentResponse = paymentService.retrieveSpecificPayment(paymentId);
        if(paymentResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PaymentResponse> addPayment(@RequestBody PaymentRequest paymentRequest){
        PaymentResponse paymentResponse = paymentService.addPayment(paymentRequest);
        return ResponseEntity.ok(paymentResponse);
    }

    @DeleteMapping(value = "/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PaymentResponse> deletePayment(@PathVariable long paymentId){
        PaymentResponse paymentResponse = paymentService.deletePayment(paymentId);
        return ResponseEntity.ok(paymentResponse);
    }

    @PutMapping(value = "/{paymentId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PaymentResponse> updatePayment(@PathVariable long paymentId,@RequestBody PaymentRequest paymentRequest){
        PaymentResponse paymentResponse = paymentService.updatePayment(paymentId,paymentRequest);
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping(value = "/{paymentId}/order", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PaymentOrderResponse> retrievePaymentOrder(@PathVariable long paymentId){
        PaymentOrderResponse paymentOrderResponse = paymentService.retrievePaymentOrder(paymentId);
        if(paymentOrderResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(paymentOrderResponse);
    }

    @GetMapping(value = "/{paymentId}/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Customer> retrievePaymentCustomer(@PathVariable long paymentId){
        Customer customer = paymentService.retrievePaymentCustomer(paymentId);
        if(customer == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customer);
    }
}
