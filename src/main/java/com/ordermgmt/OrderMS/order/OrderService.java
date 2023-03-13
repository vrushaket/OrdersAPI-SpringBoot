package com.ordermgmt.OrderMS.order;

import com.ordermgmt.OrderMS.customer.Customer;
import com.ordermgmt.OrderMS.food.Food;
import com.ordermgmt.OrderMS.payment.Payment;

import java.util.List;

public interface OrderService {

    void addOrder(Order Order);

    void deleteOrder(long customerId);

    void updateOrder(long customerId, Order Order);

    Order retrieveSpecificOrder(long customerId);

    List<Order> retrieveAllOrder();

    Payment retrieveOrderPayment(long orderId);

    List<Customer> retrieveOrderCustomer(long orderId);
}
