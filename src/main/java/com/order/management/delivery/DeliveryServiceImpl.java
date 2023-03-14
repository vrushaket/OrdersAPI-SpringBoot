package com.order.management.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryRepository repository;

    @Override
    public void addDelivery(Delivery delivery) {
        repository.save(delivery);
    }

    @Override
    public void deleteDelivery(long deliveryId) {
        Optional<Delivery> optionalDelivery = repository.findById(deliveryId);
        if (optionalDelivery.isPresent()) {
            repository.deleteById(deliveryId);
        }
    }

    @Override
    public void updateDelivery(long deliveryId, Delivery delivery) {
        Optional<Delivery> optionalDelivery = repository.findById(deliveryId);
        if (optionalDelivery.isPresent()) {
            Delivery existingDelivery = optionalDelivery.get();
            existingDelivery.setStatus(delivery.getStatus());
            repository.save(existingDelivery);
        }
    }
    
    @Override
    public Delivery retrieveSpecificDelivery(long deliveryId) {
        Optional<Delivery> delivery = repository.findById(deliveryId);
        if(delivery.isEmpty()) return null;
        return delivery.get();
    }

    @Override
    public List<Delivery> retrieveAllDelivery() {
        List<Delivery> allDelivery = repository.findAll();
        if(allDelivery.isEmpty()) return null;
        return allDelivery;
    }
}
