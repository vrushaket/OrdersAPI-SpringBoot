package com.order.management.customer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void getCustomers_shouldReturnAllCustomerResponse(){
        //given
        String expectedResponseBodyContent = """
                {id=1, name=Vrushaket, address=Pune, phone=9595068833}
                """;
        //when
        ResponseEntity<List> customersResponseEntity = restTemplate.getForEntity("/customers", List.class);
        //then
        Assertions.assertThat(customersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat( customersResponseEntity.getBody().get(0).toString()).isEqualTo(expectedResponseBodyContent.trim());
    }
}
