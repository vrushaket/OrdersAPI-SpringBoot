package com.ordermgmt.OrderMS.customer;

import com.ordermgmt.OrderMS.customer.Customer;
import com.ordermgmt.OrderMS.customer.CustomerServiceImpl;
import com.ordermgmt.OrderMS.order.Order;
import com.ordermgmt.OrderMS.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CustomerResource {

    @Autowired
    CustomerServiceImpl customerService;

    @GetMapping("/customers")
    ResponseEntity<Object> retrieveAllCustomer(){
        List<Customer> customers = customerService.retrieveAllCustomer();
        if(customers.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers/{customerId}")
    ResponseEntity<Object> retrieveSpecificCustomer(@PathVariable long customerId){
        Customer customer = customerService.retrieveSpecificCustomer(customerId);
        if(customer == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customer);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    ResponseEntity<Object> addCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{customerId}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.DELETE)
    ResponseEntity<Object> deleteCustomer(@PathVariable long customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.PUT)
    ResponseEntity<Object> updateCustomer(@RequestBody Customer customer,@PathVariable long customerId){
        customerService.updateCustomer(customerId,customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{customerId}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.ok(location);
    }

    @GetMapping("/customers/{customerId}/payments")
    ResponseEntity<Object> retrieveCustomerPayments(@PathVariable long customerId){
        List<Payment> payments = customerService.retrieveCustomerPayments(customerId);
        if(payments == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/customers/{customerId}/orders")
    ResponseEntity<Object> retrieveCustomerOrders(@PathVariable long customerId){
        List<Order> orders = customerService.retrieveCustomerOrders(customerId);
        if(orders == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customers/orders")
    ResponseEntity<Object> retrieveCustomerOrdersByPhone(@RequestParam(value = "phone", required = false) String phone){
        List<Object> orders = customerService.retrieveCustomerOrdersByPhone(phone);
        if(orders == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orders);
    }
}
