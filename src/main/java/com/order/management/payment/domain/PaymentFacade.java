package com.order.management.payment.domain;

import com.order.management.customer.domain.Customer;
import com.order.management.order.domain.Order;
import com.order.management.payment.api.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentFacade {

    private final PaymentRepository repository;

    @Autowired
    public PaymentFacade(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment addPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment(paymentRequest.getCustomerId(),paymentRequest.getAmount(), paymentRequest.getMethod(), paymentRequest.getStatus(),paymentRequest.getDate());
        return repository.save(payment);
    }

    public Payment deletePayment(long paymentId) {
        Optional<Payment> optionalPayment = repository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            repository.deleteById(paymentId);
        }
        return optionalPayment.get();
    }

    public Payment updatePayment(long paymentId, PaymentRequest paymentRequest) {
        Optional<Payment> optionalPayment = repository.findById(paymentId);
        Payment payment = null;
        if (optionalPayment.isPresent()) {
            Payment existingPayment = optionalPayment.get();
            existingPayment.setDate(paymentRequest.getDate());
            existingPayment.setAmount(paymentRequest.getAmount());
            existingPayment.setMethod(paymentRequest.getMethod());
            existingPayment.setCustomerId(paymentRequest.getCustomerId());
            payment = repository.save(existingPayment);
        }
        return payment;
    }

    public Payment retrieveSpecificPayment(long paymentId) {
        Optional<Payment> payment = repository.findById(paymentId);
        if(payment.isEmpty()) return null;
        return payment.get();
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

    public Customer retrievePaymentCustomer(long paymentId) {
        Customer customer = repository.retrievePaymentCustomer(paymentId);
        if(customer ==null) return null;
        return customer;
    }

}
