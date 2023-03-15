package com.order.management.delivery.api;

import com.order.management.delivery.api.request.DeliveryRequest;
import com.order.management.delivery.api.response.DeliveryResponse;
import com.order.management.delivery.domain.Delivery;
import com.order.management.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping()
    ResponseEntity<Object> retrieveAllDelivery(){
        List<DeliveryResponse> deliveryResponses = deliveryService.retrieveAllDelivery();
        if(deliveryResponses.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(deliveryResponses);
    }

    @GetMapping("/{deliveryId}")
    ResponseEntity<DeliveryResponse> retrieveSpecificDelivery(@PathVariable long deliveryId){
        DeliveryResponse deliveryResponse = deliveryService.retrieveSpecificDelivery(deliveryId);
        if(deliveryResponse == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(deliveryResponse);
    }

    @PostMapping()
    ResponseEntity<DeliveryResponse> addDelivery(@RequestBody DeliveryRequest deliveryRequest){
        DeliveryResponse deliveryResponse = deliveryService.addDelivery(deliveryRequest);
        return ResponseEntity.ok(deliveryResponse);
    }

    @DeleteMapping("/{deliveryId}")
    ResponseEntity<DeliveryResponse> deleteDelivery(@PathVariable long deliveryId){
        DeliveryResponse deliveryResponse = deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.ok(deliveryResponse);
    }

    @PutMapping("/{deliveryId}")
    ResponseEntity<DeliveryResponse> updateDelivery(@PathVariable long deliveryId, @RequestBody DeliveryRequest deliveryRequest){
        DeliveryResponse deliveryResponse = deliveryService.updateDelivery(deliveryId,deliveryRequest);
        return ResponseEntity.ok(deliveryResponse);
    }
}
