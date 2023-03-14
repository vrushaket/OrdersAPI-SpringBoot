package com.order.management.customer.domain;
import com.order.management.order.Order;
import com.order.management.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor {

//    @Query("""
//            select p from Payment p inner join Customer c
//            on c.id = p.customerId
//            where p.customerId= :customerId
//            """)
    @Query("""
            select payments from Customer c 
            where c.id= :customerId
            """)
    List<Payment> retrieveCustomerPayments(@Param("customerId") long customerId);

//    @Query("""
//            select o from Order o inner join Customer c
//            on c.id = o.customerId
//            where o.customerId= :customerId
//            """)
    @Query("""
            select orders from Customer c 
            where c.id= :customerId
            """)
    List<Order> retrieveCustomerOrders(long customerId);

    @Query(value = """
            select o from Order o inner join Customer c
            on o.customer.id = c.id
            where c.phone= :phone
            """)
    List<Order> retrieveCustomerOrdersByPhone(@Param("phone") String phone);

    //insert into customer (address, name, phone) values (?, ?, ?)
    //save

    //update customer set address=?, name=?, phone=? where id=?
    //update
}
