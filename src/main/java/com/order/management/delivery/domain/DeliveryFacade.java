package com.order.management.delivery.domain;

import com.order.management.delivery.api.request.DeliveryRequest;
import com.order.management.delivery.api.response.DeliveryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryFacade {

    private final DeliveryRepository repository;

    @Autowired
    public DeliveryFacade(DeliveryRepository repository) {
        this.repository = repository;
    }

    public Delivery addDelivery(DeliveryRequest deliveryRequest) {
        Delivery delivery = new Delivery(deliveryRequest.getStatus());
        return repository.save(delivery);
    }

    public Delivery deleteDelivery(long deliveryId) {
        Optional<Delivery> optionalDelivery = repository.findById(deliveryId);
        if (optionalDelivery.isPresent()) repository.deleteById(deliveryId);
        return optionalDelivery.get();
    }

    public Delivery updateDelivery(long deliveryId, DeliveryRequest deliveryRequest) {
        Optional<Delivery> optionalDelivery = repository.findById(deliveryId);
        Delivery delivery=null;
        if (optionalDelivery.isPresent()) {
            Delivery existingDelivery = optionalDelivery.get();
            existingDelivery.setStatus(deliveryRequest.getStatus());
            delivery = repository.save(existingDelivery);
        }
        return delivery;
    }

    public Delivery retrieveSpecificDelivery(long deliveryId) {
        Optional<Delivery> delivery = repository.findById(deliveryId);
        if(delivery.isEmpty()) return null;
        return delivery.get();
    }

    public List<Delivery> retrieveAllDelivery() {
        List<Delivery> allDelivery = repository.findAll();
        if(allDelivery.isEmpty()) return null;
        return allDelivery;
    }
}
