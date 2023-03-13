package com.ordermgmt.OrderMS.payment;

import java.util.List;

public interface PaymentService {


    void addPayment(Payment Payment);

    void deletePayment(long paymentId);

    void updatePayment(long paymentId, Payment Payment);

    Payment retrieveSpecificPayment(long paymentId);

    List<Payment> retrieveAllPayment();
}
