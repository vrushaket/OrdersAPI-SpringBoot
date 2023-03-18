package com.order.management.customer.domain;

import com.order.management.customer.api.request.CustomerRequest;
import com.order.management.customer.exception.CustomerNotFoundException;
import com.order.management.order.domain.Order;
import com.order.management.payment.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerFacade {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerFacade(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer(customerRequest.getName(), customerRequest.getAddress(), customerRequest.getPhone());
        return customerRepository.save(customer);
    }

    public Customer deleteCustomer(long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) customerRepository.deleteById(customerId);
        return optionalCustomer.get();
    }

    public Customer updateCustomer(long customerId, CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Customer customer = null;
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setName(customerRequest.getName());
            existingCustomer.setAddress(customerRequest.getAddress());
            existingCustomer.setPhone(customerRequest.getPhone());
            customer = customerRepository.save(existingCustomer);
        }
        return customer;
    }

    public Customer retrieveSpecificCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()) throw new CustomerNotFoundException();
        return customer.get();
    }

    public List<Customer> retrieveAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty()) return null;
        return customers;
    }

    public List<Order> retrieveCustomerOrders(long customerId) {
        List<Order> orders = customerRepository.retrieveCustomerOrders(customerId);
        return orders;
    }

    public List<Order> retrieveCustomerOrdersByPhone(String phone) {
        List<Order> orders = customerRepository.retrieveCustomerOrdersByPhone(phone);
        return orders;
    }

    public List<Payment> retrieveCustomerPayments(long customerId){
        List<Payment> payments = customerRepository.retrieveCustomerPayments(customerId);
        return payments;
    }
}
