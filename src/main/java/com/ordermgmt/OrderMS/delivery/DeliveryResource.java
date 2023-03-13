package com.ordermgmt.OrderMS.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class DeliveryResource {

    @Autowired
    DeliveryServiceImpl deliveryService;

    @GetMapping("/deliveries")
    ResponseEntity<Object> retrieveAllDelivery(){
        List<Delivery> deliverys = deliveryService.retrieveAllDelivery();
        if(deliverys.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(deliverys);
    }

    @GetMapping("/deliveries/{deliveryId}")
    ResponseEntity<Object> retrieveSpecificDelivery(@PathVariable long deliveryId){
        Delivery delivery = deliveryService.retrieveSpecificDelivery(deliveryId);
        if(delivery == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(delivery);
    }

    @RequestMapping(value = "/deliveries", method = RequestMethod.POST)
    ResponseEntity<Object> addDelivery(@RequestBody Delivery delivery){
        deliveryService.addDelivery(delivery);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{deliveryId}").buildAndExpand(delivery.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/deliveries/{deliveryId}", method = RequestMethod.DELETE)
    ResponseEntity<Object> deleteDelivery(@PathVariable long deliveryId){
        deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/deliveries/{deliveryId}", method = RequestMethod.PUT)
    ResponseEntity<Object> updateDelivery(@RequestBody Delivery delivery, @PathVariable long deliveryId){
        deliveryService.updateDelivery(deliveryId,delivery);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{deliveryId}").buildAndExpand(delivery.getId()).toUri();
        return ResponseEntity.ok(location);
    }
}
