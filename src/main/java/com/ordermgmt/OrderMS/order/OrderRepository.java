package com.ordermgmt.OrderMS.order;
import com.ordermgmt.OrderMS.customer.Customer;
import com.ordermgmt.OrderMS.delivery.Delivery;
import com.ordermgmt.OrderMS.food.Food;
import com.ordermgmt.OrderMS.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            select p from Order o inner join Payment p
            on o.paymentId = p.Id
            where o.id = :orderId
            """)
    Payment findPaymentByOrderId(@Param("orderId") long orderId);

    @Query("""
            select d from Order o inner join Delivery d
            on o.deliveryId = d.Id
            where o.id = :orderId
            """)
    Delivery findDeliveryByOrderId(@Param("orderId") long orderId);

    @Query("""
            select f from Food f inner join OrderItems oi
            on oi.foodId = f.id inner join Order o
            on o.id = oi.orderId where o.id = :orderId
            """)
    List<Food> findFoodByOrderId(@Param("orderId") long orderId);

    @Query("""
            select c from Order o inner join Customer c
            on o.customerId = c.Id
            where o.id = :orderId
            """)
    List<Customer> findCustomerByOrderId(@Param("orderId") long orderId);
}
