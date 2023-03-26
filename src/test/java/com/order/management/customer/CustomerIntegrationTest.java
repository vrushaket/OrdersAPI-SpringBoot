package com.order.management.customer;
import com.order.management.customer.api.request.CustomerRequest;
import com.order.management.customer.api.response.CustomerResponse;
import com.order.management.customer.api.response.CustomerSummary;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void getCustomers_shouldReturnAllCustomerResponse() throws JSONException {
        //given
        String expectedResponseBodyContent = """
                [{"id":1,"name":"Vrushaket","address":"Pune","phone":"9595068833"},{"id":2,"name":"Abhijeet","address":"Mumbai","phone":"9944561278"},{"id":3,"name":"Chetan","address":"Jalgaon","phone":"7588612345"},{"id":5,"name":"Anuj","address":"Alandi","phone":"8623951209"},{"id":6,"name":"Om","address":"Pune","phone":"8623951210"},{"id":12,"name":"Vrushaket","address":"Pune","phone":"9595068833"},{"id":13,"name":"Vrushaket","address":"Pune","phone":"9595068833"},{"id":14,"name":"Vrushaket","address":"Pune","phone":"9595068833"},{"id":11,"name":"Ram","address":"Pune","phone":"8877665544"},{"id":15,"name":"Vrushaket","address":"Pune","phone":"9595068833"},{"id":16,"name":"Vrushaket","address":"Pune","phone":"9595068833"},{"id":17,"name":"Vrushaket","address":"Pune","phone":"9595068833"},{"id":18,"name":"Vrushaket","address":"Pune","phone":"9595068833"},{"id":19,"name":"Vrushaket","address":"Pune","phone":"9595068833"}]
                """;
        //when
        ResponseEntity<String> customersResponseEntity = restTemplate.getForEntity("/customers", String.class);
        //then
        Assertions.assertThat(customersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(expectedResponseBodyContent.trim()).isEqualTo(customersResponseEntity.getBody().toString());
        JSONAssert.assertEquals(expectedResponseBodyContent,customersResponseEntity.getBody().toString(),false);
    }

    @Test
    public void givenCustomerId_toRetrieveCustomer_shouldReturnCustomer(){
        //given
        CustomerResponse expectedCustomerResponse = new CustomerResponse(1,"Vrushaket","Pune","9595068833");
        //when
        ResponseEntity<CustomerResponse> customerResponseEntity = restTemplate.getForEntity("/customers/1", CustomerResponse.class);
        //then
        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(expectedCustomerResponse.toString()).isEqualTo(customerResponseEntity.getBody().toString());
    }

    @Test
    public void givenCustomerRequest_toAddCustomer_shouldAddNewCustomer(){
        //given
        CustomerRequest customerRequest = new CustomerRequest("Vrushaket","Pune","9595068833");
        //when
        ResponseEntity<CustomerSummary> customersSummaryEntity = restTemplate.postForEntity("/customers",customerRequest,CustomerSummary.class);

        //then
        Assertions.assertThat(customersSummaryEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<CustomerResponse> customerResponseEntity = restTemplate.getForEntity("/customers/"+customersSummaryEntity.getBody().getId(), CustomerResponse.class);
        Assertions.assertThat(customerResponseEntity.getBody().getId()).isEqualTo(customersSummaryEntity.getBody().getId());
    }

    @Test
    public void givenCustomerId_toDeleteCustomer_shouldDeleteCustomer(){
        //given
        long customerId = 10;
        //when
        ResponseEntity<CustomerSummary> customersSummaryEntity = restTemplate.exchange("/customers/"+customerId,HttpMethod.DELETE, HttpEntity.EMPTY,CustomerSummary.class);
        //then
        Assertions.assertThat(customersSummaryEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<CustomerSummary> responseEntityCheck = restTemplate.exchange("/customers/"+customerId,HttpMethod.DELETE, HttpEntity.EMPTY,CustomerSummary.class);
        Assertions.assertThat(responseEntityCheck.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenCustomerIdAndCustomerRequest_toUpdateCustomer_shouldUpdateCustomer(){
        //given
        long customerId = 11;
        CustomerRequest customerRequest = new CustomerRequest("Ram","Pune","8877665544");
        //when
        ResponseEntity<CustomerSummary> customersSummaryEntity = restTemplate.exchange("/customers/"+customerId,HttpMethod.PUT,new HttpEntity<>(customerRequest),CustomerSummary.class);
        //then
        Assertions.assertThat(customersSummaryEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<CustomerResponse> customerResponseEntity = restTemplate.getForEntity("/customers/"+customerId, CustomerResponse.class);
        Assertions.assertThat(customerResponseEntity.getBody().getName().toString()).isEqualTo("Ram");
    }

    @Test
    public void givenCustomerId_toRetrieveCustomerPayments_shouldReturnCustomerPayments() throws JSONException {
        //given
        long customerId = 1;
        String expectedResponseBodyContent = """
                [
                  {
                      "id": 1,
                      "amount": 500,
                      "method": "UPI",
                      "status": "success",
                      "date": "2023-03-12"
                  },
                  {
                      "id": 3,
                      "amount": 1200,
                      "method": "Credit Card",
                      "status": "success",
                      "date": "2023-03-12"
                  }
                ]
                """;
        //when
        ResponseEntity<String> customersResponseEntity = restTemplate.getForEntity("/customers/"+customerId+"/payments", String.class);
        //then
        Assertions.assertThat(customersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
     //   Assertions.assertThat(expectedResponseBodyContent.trim()).isEqualTo(customersResponseEntity.getBody().toString().trim());
        JSONAssert.assertEquals(expectedResponseBodyContent,customersResponseEntity.getBody().toString(),false);
    }

    @Test
    public void givenCustomerId_toRetrieveCustomerOrders_shouldReturnCustomerOrders() throws JSONException {
        //given
        long customerId = 1;
        String expectedResponseBodyContent = """
                [{"id":6,"date":"2023-03-13","quantity":1,"foods":[{"id":1,"name":"Fries","price":80},{"id":4,"name":"Spring Roll","price":100}],"payment":{"id":6,"customerId":5,"amount":2200,"method":"UPI","status":"success","date":"2023-03-12"},"delivery":{"id":2,"status":"waiting"}},{"id":13,"date":"2023-03-12","quantity":2,"foods":[{"id":6,"name":"Burger","price":150},{"id":7,"name":"Pizza","price":200}],"payment":null,"delivery":{"id":1,"status":"on the way"}},{"id":22,"date":"2023-03-12","quantity":2,"foods":[{"id":6,"name":"Burger","price":150},{"id":7,"name":"Pizza","price":200}],"payment":null,"delivery":{"id":1,"status":"on the way"}},{"id":23,"date":"2023-03-12","quantity":2,"foods":[{"id":6,"name":"Burger","price":150},{"id":7,"name":"Pizza","price":200}],"payment":null,"delivery":{"id":1,"status":"on the way"}}]
                """;
        //when
        ResponseEntity<String> customersResponseEntity = restTemplate.getForEntity("/customers/"+customerId+"/orders", String.class);
        //then
        Assertions.assertThat(customersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //   Assertions.assertThat(expectedResponseBodyContent.trim()).isEqualTo(customersResponseEntity.getBody().toString().trim());
        JSONAssert.assertEquals(expectedResponseBodyContent,customersResponseEntity.getBody().toString(),false);
    }
}
