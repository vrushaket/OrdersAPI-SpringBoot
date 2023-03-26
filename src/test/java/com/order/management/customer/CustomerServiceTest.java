package com.order.management.customer;

import com.order.management.customer.api.request.CustomerRequest;
import com.order.management.customer.api.response.CustomerOrderResponse;
import com.order.management.customer.api.response.CustomerResponse;
import com.order.management.customer.api.response.CustomerSummary;
import com.order.management.customer.domain.Customer;
import com.order.management.customer.domain.CustomerFacade;
import com.order.management.customer.service.CustomerService;
import com.order.management.food.domain.Food;
import com.order.management.order.domain.Order;
import com.order.management.payment.api.response.PaymentResponse;
import com.order.management.payment.domain.Payment;
import org.assertj.core.api.Assertions;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerFacade customerFacade;

    private CustomerService customerService;

    @Before
    public void setUp() throws Exception{
        customerService = new CustomerService(customerFacade);
    }

    @Test
    public void givenCustomerCreateRequest_shouldAddNewCustomer(){
        //given
        CustomerRequest customerRequest = new CustomerRequest("Vrushaket","Pune","9595068833");
        Customer customer = new Customer(customerRequest.getName(),customerRequest.getAddress(),customerRequest.getPhone());
        CustomerSummary expectedCustomerSummary = new CustomerSummary(customer.getId());
        //when
        Mockito.when(customerFacade.addCustomer(customerRequest)).thenReturn(customer);
        CustomerSummary actualCustomerSummary = customerService.addCustomer(customerRequest);
        //then
        Assertions.assertThat(expectedCustomerSummary.toString()).isEqualTo(actualCustomerSummary.toString());
    }

    @Test
    public void givenCustomerId_toDeleteCustomer_shouldDeleteCustomer(){
        //given
        long customerIdToDelete = 1;
        Customer customer = new Customer("Vrushaket","Pune","9595068833");
        customer.setId(customerIdToDelete);
        CustomerSummary expectedCustomerSummary = new CustomerSummary(customerIdToDelete);
        //when
        Mockito.when(customerFacade.deleteCustomer(customerIdToDelete)).thenReturn(customer);
        CustomerSummary actualCustomerSummary = customerService.deleteCustomer(customerIdToDelete);
        //then
        Assertions.assertThat(expectedCustomerSummary.toString()).isEqualTo(actualCustomerSummary.toString());
    }

    @Test
    public void givenCustomerIdAndCustomerRequest_toUpdateCustomer_shouldUpdateCustomer(){
        //given
        long customerIdToUpdate= 1;
        CustomerRequest customerRequest = new CustomerRequest("Vrushaket","Pune","9595068833");
        Customer customer = new Customer(customerRequest.getName(),customerRequest.getAddress(),customerRequest.getPhone());
        CustomerSummary expectedCustomerSummary = new CustomerSummary(customer.getId());
        //when
        Mockito.when(customerFacade.updateCustomer(customerIdToUpdate,customerRequest)).thenReturn(customer);
        CustomerSummary actualCustomerSummary = customerService.updateCustomer(customerIdToUpdate,customerRequest);
        //then
        Assertions.assertThat(expectedCustomerSummary.toString()).isEqualTo(actualCustomerSummary.toString());
    }

    @Test
    public void givenCustomerId_toRetrieveCustomerRequest_shouldReturnCustomerResponse(){
        //given
        long customerId = 1;
        Customer customer = new Customer("Vrushaket","Pune","9595068833");
        customer.setId(customerId);
        CustomerResponse expectedCustomerSummary = new CustomerResponse(1,customer.getName(),customer.getAddress(),customer.getPhone());
        BDDMockito.given(customerFacade.retrieveSpecificCustomer(customerId)).willReturn(customer);
        //when
        CustomerResponse actualCustomerResponse = customerService.retrieveSpecificCustomer(customerId);
        //then
        Assertions.assertThat(expectedCustomerSummary.toString()).isEqualTo(actualCustomerResponse.toString());
    }

    @Test
    public void givenRequestToRetrieveAllCustomer_shouldReturnAllCustomersResponse(){
        //given
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer("Vrushaket","Pune","9595068833");
        Customer customer2 = new Customer("Om","Pune","7558662599");
        customers.add(customer1);
        customers.add(customer2);

        List<CustomerResponse> expectedCustomerResponses = new ArrayList<>();
        for(Customer customer: customers){
            expectedCustomerResponses.add(new CustomerResponse(customer.getId(),customer.getName(),customer.getAddress(),customer.getPhone()));
        }
        //when
        Mockito.when(customerFacade.retrieveAllCustomer()).thenReturn(customers);
        List<CustomerResponse> actualCustomerResponses = customerService.retrieveAllCustomer();
        //then
        Assertions.assertThat(expectedCustomerResponses.toString()).isEqualTo(actualCustomerResponses.toString());
    }

    @Test
    public void givenCustomerId__toRetrieveCustomerOrders_shouldReturnCustomerOrderResponse(){
        long customerId = 1l;
        List<Order> orders = new ArrayList<>();
        List<Food> foodList = new ArrayList<>();
        Food food = new Food("Burger",100);
        foodList.add(food);
        Order order1 = new Order(1l,1l,1l, new Date(2023,03,18),2,foodList);
        orders.add(order1);
        List<CustomerOrderResponse> expectedCustomerOrderResponses = new ArrayList<>();
        for(Order order: orders){
            expectedCustomerOrderResponses.add(new CustomerOrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getPayment(),order.getDelivery()));
        }
        //when
        Mockito.when(customerFacade.retrieveCustomerOrders(customerId)).thenReturn(orders);
        List<CustomerOrderResponse> actualCustomerOrderResponses = customerService.retrieveCustomerOrders(customerId);
        //then
        Assertions.assertThat(expectedCustomerOrderResponses.toString()).isEqualTo(actualCustomerOrderResponses.toString());
    }

    @Test
    public void givenCustomerPhone_toRetrieveCustomerOrderByPhone_shouldReturnCustomerOrderResponse(){
        long customerId = 1l;
        List<Order> orders = new ArrayList<>();
        List<Food> foodList = new ArrayList<>();
        Food food = new Food("Burger",100);
        foodList.add(food);
        Order order1 = new Order(1l,1l,1l, new Date(2023,03,18),2,foodList);
        orders.add(order1);
        List<CustomerOrderResponse> expectedCustomerOrderResponses = new ArrayList<>();
        for(Order order: orders){
            expectedCustomerOrderResponses.add(new CustomerOrderResponse(order.getId(),order.getDate(),order.getQuantity(),order.getFoods(),order.getPayment(),order.getDelivery()));
        }
        //when
        Mockito.when(customerFacade.retrieveCustomerOrders(customerId)).thenReturn(orders);
        List<CustomerOrderResponse> actualCustomerOrderResponses = customerService.retrieveCustomerOrders(customerId);
        //then
        Assertions.assertThat(expectedCustomerOrderResponses.toString()).isEqualTo(actualCustomerOrderResponses.toString());
    }

    @Test
    public void givenCustomerId_toRetrieveCustomerPayments_shouldReturnCustomerPayments(){
        //given
        long customerId = 1l;
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(1l,100,"UPI","success",new Date(2023,03,18)));
        payments.add(new Payment(1l,200,"Debit Card","failed",new Date(2023,03,18)));
        List<PaymentResponse> expectedPaymentResponses = new ArrayList<>();
        for(Payment payment: payments){
            expectedPaymentResponses.add(new PaymentResponse(payment.getId(),payment.getAmount(),payment.getMethod(),payment.getStatus(),payment.getDate()));
        }
        //when
        Mockito.when(customerFacade.retrieveCustomerPayments(customerId)).thenReturn(payments);
        List<PaymentResponse> actualPaymentResponses = customerService.retrieveCustomerPayments(customerId);
        //then
        Assertions.assertThat(expectedPaymentResponses.toString()).isEqualTo(actualPaymentResponses.toString());
    }
}