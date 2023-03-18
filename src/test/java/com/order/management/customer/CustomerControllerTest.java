package com.order.management.customer;

import com.order.management.customer.api.CustomerController;
import com.order.management.customer.api.request.CustomerRequest;
import com.order.management.customer.api.response.CustomerOrderResponse;
import com.order.management.customer.api.response.CustomerResponse;
import com.order.management.customer.api.response.CustomerSummary;
import com.order.management.customer.exception.CustomerNotFoundException;
import com.order.management.customer.service.CustomerService;
import com.order.management.delivery.domain.Delivery;
import com.order.management.food.domain.Food;
import com.order.management.payment.api.response.PaymentResponse;
import com.order.management.payment.domain.Payment;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)  //doesn't start all the component, but start only the necessary components
public class CustomerControllerTest {

    private static String GENERIC_CUSTOMERS_URL = "http://localhost:8000/customers";
    private static String SPECIFIC_CUSTOMERS_URL = "http://localhost:8000/customers/1";
    private static String SPECIFIC_CUSTOMER_PAYMENTS_URL = "http://localhost:8000/customers/1/payments";
    private static String SPECIFIC_CUSTOMER_ORDERS_URL = "http://localhost:8000/customers/1/orders";

    @Autowired
    private MockMvc mockMvc;

    //why failing with @Mock
    @MockBean
    private CustomerService customerService;

    @Test
    public void getCustomers_shouldReturnAllCustomerResponse() throws Exception {
        //given
        String expectedCustomersResponse = """
                [{"id":1,"name":"Vrushaket","address":"Pune","phone":"9595068833"},{"id":2,"name":"Anuj","address":"Alandi","phone":"8623951209"}]
                """;
        List<CustomerResponse> customerResponses = new ArrayList<>();
        customerResponses.add(new CustomerResponse(1,"Vrushaket","Pune","9595068833"));
        customerResponses.add(new CustomerResponse(2,"Anuj","Alandi","8623951209"));
        given(customerService.retrieveAllCustomer()).willReturn(customerResponses);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                        .get(GENERIC_CUSTOMERS_URL))
                                        .andExpect(status().isOk())
                                        .andReturn();

        //then
        System.out.println(mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(expectedCustomersResponse.trim()).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void givenCustomerId_shouldReturnCustomerResponse() throws Exception {
        //given
        long customerId = 1l;
        String expectedCustomerResponse = """
                {"id":1,"name":"Vrushaket","address":"Pune","phone":"9595068833"}
                """;
        CustomerResponse exptectedCustomerResponse = new CustomerResponse(1, "Vrushaket", "Pune", "9595068833");
        given(customerService.retrieveSpecificCustomer(customerId)).willReturn(exptectedCustomerResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                        .get(SPECIFIC_CUSTOMERS_URL))
                                        .andExpect(status().isOk())
                                        .andReturn();

        //then
        System.out.println(mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(expectedCustomerResponse.trim()).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void givenUnknownCustomerId_willThrowNotFoundStatus() throws Exception {
        //given
        given(customerService.retrieveSpecificCustomer(anyLong())).willThrow(new CustomerNotFoundException());
        //when
        mockMvc.perform(MockMvcRequestBuilders.get(SPECIFIC_CUSTOMERS_URL)).andExpect(status().isNotFound());
    }

    @Test
    public void givenCustomerRequest_shouldAddNewCustomer() throws Exception {
        //given
        String customerRequest= """
                {"name":"Raj","address":"Pune","phone":"8877665544"}
                """;
        CustomerRequest customerRequestBody = new CustomerRequest("Raj","Pune","8877665544");
        CustomerSummary customerSummary = new CustomerSummary(anyLong());
        when(customerService.addCustomer(customerRequestBody)).thenReturn(customerSummary);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                            .post(GENERIC_CUSTOMERS_URL)
                                            .accept(MediaType.APPLICATION_JSON)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(customerRequest))
                                        .andExpect(status().isOk())
                                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                        .andReturn();
        //then
        System.out.println("res: "+mvcResult.getResponse().getContentAsString());
       // Assertions.assertThat(expectedCustomerResponse.trim()).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void givenCustomerId_shouldDeleteAssociatedCustomer() throws Exception {
        //given
        long customerId = 1l;
        CustomerSummary customerSummary = new CustomerSummary(customerId);
        when(customerService.deleteCustomer(customerId)).thenReturn(customerSummary);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete(SPECIFIC_CUSTOMERS_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        System.out.println("res: "+mvcResult.getResponse().getContentAsString());
        // Assertions.assertThat(expectedCustomerResponse.trim()).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void givenCustomerRequestAndId_shouldUpdateCustomer() throws Exception {
        //given
        long customerId = 1l;
        String customerRequest= """
                {"name":"Raj","address":"Pune","phone":"8877665544"}
                """;
        CustomerRequest customerRequestBody = new CustomerRequest("Raj","Pune","8877665544");
        CustomerSummary customerSummary = new CustomerSummary(customerId);
        when(customerService.updateCustomer(customerId,customerRequestBody)).thenReturn(customerSummary);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(SPECIFIC_CUSTOMERS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        System.out.println("res: "+mvcResult.getResponse().getContentAsString());
        // Assertions.assertThat(expectedCustomerResponse.trim()).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void givenCustomerId_shouldReturnCustomerPaymentResponse() throws Exception {
        //given
        long customerId = 1;
        String expectedCustomerResponse = """
                [{"id":1,"amount":500,"method":"UPI","status":"success","date":"3923-04-12"},{"id":3,"amount":1200,"method":"Credit Card","status":"success","date":"3923-04-12"}]
                """;
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        paymentResponses.add(new PaymentResponse(1l,500,"UPI","success",new Date(2023,03,12)));
        paymentResponses.add(new PaymentResponse(3l,1200,"Credit Card","success",new Date(2023,03,12)));
        given(customerService.retrieveCustomerPayments(customerId)).willReturn(paymentResponses);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(SPECIFIC_CUSTOMER_PAYMENTS_URL))
                        .andExpect(status().isOk())
                        .andReturn();

        //then
        System.out.println(mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(expectedCustomerResponse.trim()).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void givenCustomerId_shouldReturnCustomerOrderResponse() throws Exception {
        //given
        long customerId = 1;
        String expectedOrderResponse = """
                [{"id":0,"date":"3923-04-12","quantity":1,"foods":[{"id":0,"name":"Fries","price":80}],"payment":{"id":0,"customerId":5,"amount":2200,"method":"UPI","status":"success","date":"3923-04-12"},"delivery":{"id":0,"status":"waiting"}}]
                """;
        List<Food> foods = new ArrayList<>();
        foods.add(new Food("Fries",80));
        Payment payment = new Payment(5l,2200,"UPI","success",new Date(2023,03,12));
        Delivery delivery = new Delivery("waiting");
        List<CustomerOrderResponse> orderResponses = new ArrayList<>();
        orderResponses.add(new CustomerOrderResponse(0l,new Date(2023,03,12),1,foods,payment,delivery));
        given(customerService.retrieveCustomerOrders(customerId)).willReturn(orderResponses);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                        .get(SPECIFIC_CUSTOMER_ORDERS_URL))
                                        .andExpect(status().isOk())
                                        .andReturn();
        //then
        System.out.println(mvcResult.getResponse().getContentAsString());
        Assertions.assertThat(expectedOrderResponse.trim()).isEqualTo(mvcResult.getResponse().getContentAsString());
    }
}
