package com.order.management.order;

import com.order.management.customer.domain.Customer;
import com.order.management.payment.Payment;

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
