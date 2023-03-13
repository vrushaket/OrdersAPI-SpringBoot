package com.ordermgmt.OrderMS.payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    //save
    //insert into payment (amount, customer_id, date, method, status) values (?, ?, ?, ?, ?)

    //get payment by phone
    //select p from payment p inner join customer c on c.id = p.customer_id where c.phone='9595068833'
}
