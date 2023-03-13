package com.ordermgmt.OrderMS.customer;
import com.ordermgmt.OrderMS.order.Order;
import com.ordermgmt.OrderMS.payment.Payment;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
            select p from Payment p inner join Customer c
            on c.id = p.customerId
            where p.customerId= :customerId
            """)
    List<Payment> retrieveCustomerPayments(@Param("customerId") long customerId);

    @Query("""
            select o from Order o inner join Customer c
            on c.id = o.customerId
            where o.customerId= :customerId
            """)
    List<Order> retrieveCustomerOrders(long customerId);

    @Query(value = """
            select o from orders o inner join customer c\s
            on o.customer_id = c.id
            where c.phone= :phone
            """,nativeQuery = true)
    List<Object> retrieveCustomerOrdersByPhone(@Param("phone") String phone);

    //insert into customer (address, name, phone) values (?, ?, ?)
    //save

    //update customer set address=?, name=?, phone=? where id=?
    //update


}
