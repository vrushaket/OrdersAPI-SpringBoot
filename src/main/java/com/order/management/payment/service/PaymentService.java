package com.order.management.payment.service;

import com.order.management.customer.domain.Customer;
import com.order.management.order.domain.Order;
import com.order.management.payment.api.request.PaymentRequest;
import com.order.management.payment.api.response.PaymentOrderResponse;
import com.order.management.payment.api.response.PaymentResponse;
import com.order.management.payment.domain.Payment;
import com.order.management.payment.domain.PaymentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentFacade paymentFacade;

    public PaymentResponse addPayment(PaymentRequest paymentRequest) {
        Payment payment = paymentFacade.addPayment(paymentRequest);
        return new PaymentResponse(payment.getId(),payment.getAmount(),payment.getMethod(),payment.getStatus(),payment.getDate());
    }

    public PaymentResponse deletePayment(long paymentId) {
        Payment payment = paymentFacade.deletePayment(paymentId);
        return new PaymentResponse(payment.getId(),payment.getAmount(),payment.getMethod(),payment.getStatus(),payment.getDate());
    }

    public PaymentResponse updatePayment(long paymentId, PaymentRequest paymentRequest) {
        Payment payment = paymentFacade.updatePayment(paymentId,paymentRequest);
        return new PaymentResponse(payment.getId(),payment.getAmount(),payment.getMethod(),payment.getStatus(),payment.getDate());
    }

    public PaymentResponse retrieveSpecificPayment(long paymentId) {
        Payment payment = paymentFacade.retrieveSpecificPayment(paymentId);
        return new PaymentResponse(payment.getId(),payment.getAmount(),payment.getMethod(),payment.getStatus(),payment.getDate());
    }

    public List<PaymentResponse> retrieveAllPayment() {
        List<Payment> payments = paymentFacade.retrieveAllPayment();
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for(Payment payment: payments){
            paymentResponses.add(new PaymentResponse(payment.getId(),payment.getAmount(),payment.getMethod(),payment.getStatus(),payment.getDate()));
        }
        return paymentResponses;
    }

    public PaymentOrderResponse retrievePaymentOrder(long paymentId) {
        Order order = paymentFacade.retrievePaymentOrder(paymentId);
        return new PaymentOrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getCustomer(),order.getDelivery());
    }

    public Customer retrievePaymentCustomer(long paymentId) {
        Customer customer = paymentFacade.retrievePaymentCustomer(paymentId);
        return customer;
    }
}
