package com.order.management.customer.api;

import com.order.management.customer.api.response.CustomerOrderResponse;
import com.order.management.customer.api.response.CustomerResponse;
import com.order.management.customer.exception.CustomerNotFoundException;
import com.order.management.customer.service.CustomerService;
import com.order.management.customer.api.request.CustomerRequest;
import com.order.management.customer.api.response.CustomerSummary;
import com.order.management.order.api.response.OrderResponse;
import com.order.management.order.domain.Order;
import com.order.management.payment.api.response.PaymentResponse;
import com.order.management.payment.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping()
    ResponseEntity<List<CustomerResponse>> retrieveAllCustomer(){
        List<CustomerResponse> customerResponses = customerService.retrieveAllCustomer();
        if(customerResponses.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customerResponses);
    }

    @GetMapping("/{customerId}")
    ResponseEntity<CustomerResponse> retrieveSpecificCustomer(@PathVariable long customerId){
        CustomerResponse customerResponse = customerService.retrieveSpecificCustomer(customerId);
        if(customerResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping()
    ResponseEntity<CustomerSummary> addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerSummary customerSummary = customerService.addCustomer(customerRequest);
        return ResponseEntity.ok(customerSummary);
    }

    @DeleteMapping("/{customerId}")
    ResponseEntity<CustomerSummary> deleteCustomer(@PathVariable long customerId){
        CustomerSummary customerSummary = customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(customerSummary);
    }

    @PutMapping("/{customerId}")
    ResponseEntity<CustomerSummary> updateCustomer(@RequestBody CustomerRequest customerRequest,@PathVariable long customerId){
        CustomerSummary customerSummary = customerService.updateCustomer(customerId, customerRequest);
        return ResponseEntity.ok(customerSummary);
    }

    @GetMapping("/{customerId}/orders")
    ResponseEntity<List<CustomerOrderResponse>> retrieveCustomerOrders(@PathVariable long customerId){
        List<CustomerOrderResponse> customerOrderResponses = customerService.retrieveCustomerOrders(customerId);
        if(customerOrderResponses == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customerOrderResponses);
    }

    @GetMapping("/{customerId}/payments")
    ResponseEntity<List<PaymentResponse>> retrieveCustomerPayments(@PathVariable long customerId){
        List<PaymentResponse> payments = customerService.retrieveCustomerPayments(customerId);
        if(payments == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/orders")
    ResponseEntity<List<CustomerOrderResponse>> retrieveCustomerOrdersByPhone(@RequestParam(value = "phone", required = false) String phone){
        List<CustomerOrderResponse> orderResponses = customerService.retrieveCustomerOrdersByPhone(phone);
        if(orderResponses == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderResponses);
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    private void CustomerNotFoundHandler(CustomerNotFoundException customerNotFoundException){}
}
