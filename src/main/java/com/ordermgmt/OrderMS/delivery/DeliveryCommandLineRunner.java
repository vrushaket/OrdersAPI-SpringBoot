package com.ordermgmt.OrderMS.delivery;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DeliveryCommandLineRunner implements CommandLineRunner {

    @Autowired
    DeliveryServiceImpl deliveryService;

    @Override
    public void run(String... args) throws Exception {
//        Delivery delivery1 = new Delivery("on the way");
//        Delivery delivery2 = new Delivery("waiting");
//        Delivery delivery3 = new Delivery("delivered");
//        Delivery delivery4 = new Delivery("canceled");
//        deliveryService.addDelivery(delivery1);
//        deliveryService.addDelivery(delivery2);
//        deliveryService.addDelivery(delivery3);
//        deliveryService.addDelivery(delivery4);
    }
}
