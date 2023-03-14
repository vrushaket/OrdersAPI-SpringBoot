package com.order.management.customer.service;

import com.order.management.customer.domain.Customer;
import com.order.management.customer.domain.CustomerFacade;
import com.order.management.customer.api.request.CustomerRequest;
import com.order.management.customer.api.response.CustomerSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
