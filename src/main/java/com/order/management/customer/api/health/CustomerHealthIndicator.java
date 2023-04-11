package com.order.management.customer.api.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerHealthIndicator extends AbstractHealthIndicator {

    public String getEndpointData() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/customers";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        String endpointData = getEndpointData();
        if (endpointData != null && !endpointData.isEmpty()) {
            builder.up();
//                    .withDetail("response", endpointData);
        } else {
            builder.down().withDetail("error", "Could not retrieve data from Customer Controller");
        }
    }
}