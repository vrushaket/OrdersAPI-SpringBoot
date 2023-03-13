package com.ordermgmt.OrderMS.customer;

import com.ordermgmt.OrderMS.order.Order;
import com.ordermgmt.OrderMS.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository repository;

    
    public void addCustomer(Customer Customer) {
        repository.save(Customer);
    }

    public void deleteCustomer(long customerId) {
        Optional<Customer> optionalCustomer = repository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            repository.deleteById(customerId);
        }
    }

    public void updateCustomer(long customerId, Customer Customer) {
        Optional<Customer> optionalCustomer = repository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setName(Customer.getName());
            existingCustomer.setAddress(Customer.getAddress());
            existingCustomer.setPhone(Customer.getPhone());
            repository.save(existingCustomer);
        }
    }

    public Customer retrieveSpecificCustomer(long customerId) {
        Optional<Customer> Customer = repository.findById(customerId);
        if(Customer.isEmpty()) return null;
        return Customer.get();
    }

    public List<Customer> retrieveAllCustomer() {
        List<Customer> allCustomer = repository.findAll();
        if(allCustomer.isEmpty()) return null;
        return allCustomer;
    }

    @Override
    public List<Order> retrieveCustomerOrders(long customerId) {
        List<Order> orders = repository.retrieveCustomerOrders(customerId);
        return orders;
    }

    @Override
    public List<Object> retrieveCustomerOrdersByPhone(String phone) {
        List<Object> orders = repository.retrieveCustomerOrdersByPhone(phone);
        return orders;
    }

    public List<Payment> retrieveCustomerPayments(long customerId){
        List<Payment> payments = repository.retrieveCustomerPayments(customerId);
        return payments;
    }
}
