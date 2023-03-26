package com.order.management.customer.service;

import com.order.management.customer.api.response.CustomerOrderResponse;
import com.order.management.customer.api.response.CustomerResponse;
import com.order.management.customer.domain.Customer;
import com.order.management.customer.domain.CustomerFacade;
import com.order.management.customer.api.request.CustomerRequest;
import com.order.management.customer.api.response.CustomerSummary;
import com.order.management.order.domain.Order;
import com.order.management.payment.api.response.PaymentResponse;
import com.order.management.payment.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private final CustomerFacade customerFacade;

    @Autowired
    public CustomerService(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public CustomerSummary addCustomer(CustomerRequest customerRequest) {
        Customer customer = customerFacade.addCustomer(customerRequest);
        return new CustomerSummary(customer.getId());
    }

    public CustomerSummary deleteCustomer(long customerId) {
        Customer customer = customerFacade.deleteCustomer(customerId);
        return new CustomerSummary(customer.getId());
    }

    public CustomerSummary updateCustomer(long customerId, CustomerRequest customerRequest) {
        Customer customer = customerFacade.updateCustomer(customerId,customerRequest);
        return new CustomerSummary(customer.getId());
    }

    public CustomerResponse retrieveSpecificCustomer(long customerId) {
        Customer customer = customerFacade.retrieveSpecificCustomer(customerId);
        return new CustomerResponse(customer.getId(),customer.getName(),customer.getAddress(),customer.getPhone());
    }

    public List<CustomerResponse> retrieveAllCustomer() {
        List<Customer> customers = customerFacade.retrieveAllCustomer();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer customer: customers){
            customerResponses.add(new CustomerResponse(customer.getId(),customer.getName(),customer.getAddress(),customer.getPhone()));
        }
        return customerResponses;
    }

    public List<CustomerOrderResponse> retrieveCustomerOrders(long customerId) {
        List<Order> orders = customerFacade.retrieveCustomerOrders(customerId);
        List<CustomerOrderResponse> customerOrderResponses = new ArrayList<>();
        for(Order order: orders){
            customerOrderResponses.add(new CustomerOrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getPayment(),order.getDelivery()));
        }
        return customerOrderResponses;
    }

    public List<CustomerOrderResponse> retrieveCustomerOrdersByPhone(String phone) {
        List<Order> orders = customerFacade.retrieveCustomerOrdersByPhone(phone);
        List<CustomerOrderResponse> customerOrderResponses = new ArrayList<>();
        for(Order order: orders){
            customerOrderResponses.add(new CustomerOrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getPayment(),order.getDelivery()));
        }
        return customerOrderResponses;
    }

    public List<PaymentResponse> retrieveCustomerPayments(long customerId){
        List<Payment> payments = customerFacade.retrieveCustomerPayments(customerId);
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for(Payment payment: payments){
            paymentResponses.add(new PaymentResponse(payment.getId(),payment.getAmount(),payment.getMethod(),payment.getStatus(),payment.getDate()));
        }
        return paymentResponses;
    }
}
