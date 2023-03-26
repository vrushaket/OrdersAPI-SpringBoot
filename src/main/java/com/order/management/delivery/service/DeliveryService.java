package com.order.management.delivery.service;

import com.order.management.delivery.api.request.DeliveryRequest;
import com.order.management.delivery.api.response.DeliveryResponse;
import com.order.management.delivery.domain.Delivery;
import com.order.management.delivery.domain.DeliveryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    DeliveryFacade deliveryFacade;

    public DeliveryResponse addDelivery(DeliveryRequest deliveryRequest) {
        Delivery delivery = deliveryFacade.addDelivery(deliveryRequest);
        return new DeliveryResponse(delivery.getId(),delivery.getStatus());
    }

    public DeliveryResponse deleteDelivery(long deliveryId) {
        Delivery delivery = deliveryFacade.deleteDelivery(deliveryId);
        return new DeliveryResponse(delivery.getId(),delivery.getStatus());
    }

    public DeliveryResponse updateDelivery(long deliveryId, DeliveryRequest deliveryRequest) {
        Delivery delivery = deliveryFacade.updateDelivery(deliveryId,deliveryRequest);
        return new DeliveryResponse(delivery.getId(),delivery.getStatus());
    }

    public DeliveryResponse retrieveSpecificDelivery(long deliveryId) {
        Delivery delivery = deliveryFacade.retrieveSpecificDelivery(deliveryId);
        return new DeliveryResponse(delivery.getId(),delivery.getStatus());
    }

    public List<DeliveryResponse> retrieveAllDelivery() {
        List<Delivery> deliveries = deliveryFacade.retrieveAllDelivery();
        List<DeliveryResponse> deliveryResponses = new ArrayList<>();
        for (Delivery delivery: deliveries) {
            deliveryResponses.add(new DeliveryResponse(delivery.getId(),delivery.getStatus()));
        }
        return deliveryResponses;
    }
}
