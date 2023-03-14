package com.ordermgmt.OrderMS.payment;

import com.ordermgmt.OrderMS.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository repository;

    
    public void addPayment(Payment payment) {
        repository.save(payment);
    }

    public void deletePayment(long paymentId) {
        Optional<Payment> optionalPayment = repository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            repository.deleteById(paymentId);
        }
    }

    public void updatePayment(long paymentId, Payment Payment) {
        Optional<Payment> optionalPayment = repository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment existingPayment = optionalPayment.get();
            existingPayment.setDate(Payment.getDate());
            existingPayment.setAmount(Payment.getAmount());
            existingPayment.setMethod(Payment.getMethod());
            existingPayment.setCustomerId(Payment.getCustomerId());
            repository.save(existingPayment);
        }
    }

    public Payment retrieveSpecificPayment(long paymentId) {
        Optional<Payment> Payment = repository.findById(paymentId);
        if(Payment.isEmpty()) return null;
        return Payment.get();
    }

    public List<Payment> retrieveAllPayment() {
        List<Payment> allPayment = repository.findAll();
        if(allPayment.isEmpty()) return null;
        return allPayment;
    }

    public Order retrievePaymentOrder(long paymentId) {
        Order order = repository.retrievePaymentOrder(paymentId);
        if(order ==null) return null;
        return order;
    }
}
