package com.order.management.customer.domain;

import com.order.management.customer.api.request.CustomerRequest;
import com.order.management.order.Order;
import com.order.management.payment.Payment;
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

    public void deleteCustomer(long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            customerRepository.deleteById(customerId);
        }
    }

    public void updateCustomer(long customerId, Customer Customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setName(Customer.getName());
            existingCustomer.setAddress(Customer.getAddress());
            existingCustomer.setPhone(Customer.getPhone());
            customerRepository.save(existingCustomer);
        }
    }

    public Customer retrieveSpecificCustomer(long customerId) {
        Optional<Customer> Customer = customerRepository.findById(customerId);
        if(Customer.isEmpty()) return null;
        return Customer.get();
    }

    public List<Customer> retrieveAllCustomer() {
        List<Customer> allCustomer = customerRepository.findAll();
        if(allCustomer.isEmpty()) return null;
        return allCustomer;
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
