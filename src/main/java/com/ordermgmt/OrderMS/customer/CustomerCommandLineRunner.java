package com.ordermgmt.OrderMS.customer;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class CustomerCommandLineRunner implements CommandLineRunner {

    @Autowired
    CustomerServiceImpl customerService;

    @Override
    public void run(String... args) throws Exception {
//        Customer customer1 = new Customer("Vrushaket","Pune","9595068833");
//        Customer customer2 = new Customer("Anuj","Alandi","8623951209");
//        Customer customer3 = new Customer("Abhijeet","Mumbai","9944561278");
//        customerService.addCustomer(customer1);
//        customerService.addCustomer(customer2);
//        customerService.addCustomer(customer3);
    }
}
