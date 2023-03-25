package com.order.management.customer.domain;
import com.order.management.food.api.response.FoodResponse;
import com.order.management.order.domain.Order;
import com.order.management.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor {

    @Query("""
            select payments from Customer c 
            where c.id= :customerId
            """)
    List<Payment> retrieveCustomerPayments(@Param("customerId") long customerId);

    @Query("""
            select orders from Customer c 
            where c.id= :customerId
            """)
    List<Order> retrieveCustomerOrders(@Param("customerId") long customerId);

    @Query(value = """
            select o from Order o inner join Customer c
            on o.customer.id = c.id
            where c.phone= :phone
            """)
    List<Order> retrieveCustomerOrdersByPhone(@Param("phone") String phone);

}
