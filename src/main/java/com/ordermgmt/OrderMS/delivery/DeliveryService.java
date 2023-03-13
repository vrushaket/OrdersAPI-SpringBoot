package com.ordermgmt.OrderMS.delivery;

import java.util.List;

public interface DeliveryService {
    void addDelivery(Delivery delivery);
    void deleteDelivery(long deliveryId);
    void updateDelivery(long deliveryId, Delivery delivery);
    Delivery retrieveSpecificDelivery(long deliveryId);
    List<Delivery> retrieveAllDelivery();
}
