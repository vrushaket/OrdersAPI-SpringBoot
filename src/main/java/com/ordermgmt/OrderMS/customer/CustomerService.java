package com.ordermgmt.OrderMS.customer;

import com.ordermgmt.OrderMS.order.Order;

import java.util.List;

public interface CustomerService {


    void addCustomer(Customer Customer);

    void deleteCustomer(long customerId);

    void updateCustomer(long customerId, Customer Customer);

    Customer retrieveSpecificCustomer(long customerId);

    List<Customer> retrieveAllCustomer();

    List<Order> retrieveCustomerOrders(long customerId);

    List<Object> retrieveCustomerOrdersByPhone(String phone);
}
