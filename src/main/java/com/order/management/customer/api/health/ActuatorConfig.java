package com.order.management.customer.api.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

    @Autowired
    private CustomerHealthIndicator customerHealthIndicator;

    @Bean
    public HealthIndicator myEndpointHealth() {
        return customerHealthIndicator;
    }
}
