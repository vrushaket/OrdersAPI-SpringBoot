package com.order.management.payment.domain;
import com.order.management.customer.domain.Customer;
import com.order.management.order.domain.Order;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select order from Payment p where p.id = :paymentId ")
    Order retrievePaymentOrder(@Param("paymentId") long paymentId);

    @Query("select p.customer from Payment p where p.id = :paymentId ")
    Customer retrievePaymentCustomer(@Param("paymentId") long paymentId);
}
