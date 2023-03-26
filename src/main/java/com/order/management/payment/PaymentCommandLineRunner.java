package com.order.management.payment;

import com.order.management.payment.domain.PaymentRepository;
import com.order.management.payment.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class PaymentCommandLineRunner implements CommandLineRunner {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public void run(String... args) throws Exception {
//        Payment payment1 = new Payment(1l,500,"UPI","success",new java.sql.Date(System.currentTimeMillis()));
//        Payment payment2 = new Payment(2l,200,"Net Banking","success",new java.sql.Date(System.currentTimeMillis()));
//        Payment payment3 = new Payment(1l,1000,"Debit Card","success",new java.sql.Date(System.currentTimeMillis()));
//        Payment payment4 = new Payment(3l,200,"UPI","success",new java.sql.Date(System.currentTimeMillis()));
//
//        paymentService.addPayment(payment1);
//        paymentService.addPayment(payment2);
//        paymentService.addPayment(payment3);
//        paymentService.addPayment(payment4);
        paymentRepository.retrievePaymentOrder(1);

    }
}
