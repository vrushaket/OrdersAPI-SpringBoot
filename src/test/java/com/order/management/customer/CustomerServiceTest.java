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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CustomerServiceTest {

    @Mock
    private CustomerFacade customerFacade;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void givenCustomerRequest_shouldAddNewCustomer(){
        //given
        CustomerRequest customerRequest = new CustomerRequest("Vrushaket","Pune","9595068833");
        Customer customer = new Customer(customerRequest.getName(),customerRequest.getAddress(),customerRequest.getPhone());
        CustomerSummary expectedCustomerSummary = new CustomerSummary(customer.getId());
        //when
        Mockito.when(customerFacade.addCustomer(customerRequest)).thenReturn(customer);
        //then
        CustomerSummary actualCustomerSummary = customerService.addCustomer(customerRequest);
        Assertions.assertThat(expectedCustomerSummary.getId()).isEqualTo(actualCustomerSummary.getId());
    }

    @Test
    public void givenCustomerId_shouldDeleteCustomer(){
        //given
        long customerIdToDelete = 1;
        Customer customer = new Customer("Vrushaket","Pune","9595068833");
        customer.setId(customerIdToDelete);
        CustomerSummary expectedCustomerSummary = new CustomerSummary(customerIdToDelete);
        //when
        Mockito.when(customerFacade.deleteCustomer(customerIdToDelete)).thenReturn(customer);
        //then
        CustomerSummary actualCustomerSummary = customerService.deleteCustomer(customerIdToDelete);
        Assertions.assertThat(expectedCustomerSummary.getId()).isEqualTo(actualCustomerSummary.getId());
    }

    @Test
    public void givenCustomerIdAndCustomerRequest_shouldUpdateCustomer(){
        //given
        long customerIdToUpdate= 1;
        CustomerRequest customerRequest = new CustomerRequest("Vrushaket","Pune","9595068833");
        Customer customer = new Customer(customerRequest.getName(),customerRequest.getAddress(),customerRequest.getPhone());
        CustomerSummary expectedCustomerSummary = new CustomerSummary(customer.getId());
        //when
        Mockito.when(customerFacade.updateCustomer(customerIdToUpdate,customerRequest)).thenReturn(customer);
        //then
        CustomerSummary actualCustomerSummary = customerService.updateCustomer(customerIdToUpdate,customerRequest);
        Assertions.assertThat(expectedCustomerSummary.toString()).isEqualTo(actualCustomerSummary.toString());
    }

    @Test
    void givenCustomerId_shouldReturnCustomerResponse(){
        //given
        long customerId = 1;
        Customer customer = new Customer("Vrushaket","Pune","9595068833");
        customer.setId(customerId);
        CustomerResponse expectedCustomerSummary = new CustomerResponse(1,customer.getName(),customer.getAddress(),customer.getPhone());
        //when
        Mockito.when(customerFacade.retrieveSpecificCustomer(customerId)).thenReturn(customer);
        //then
        CustomerResponse actualCustomerResponse = customerService.retrieveSpecificCustomer(customerId);
        Assertions.assertThat(expectedCustomerSummary.toString()).isEqualTo(actualCustomerResponse.toString());
    }

    @Test
    public void methodShouldReturnAllCustomers(){
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
        //then
        List<CustomerResponse> actualCustomerResponses = customerService.retrieveAllCustomer();
        Assertions.assertThat(expectedCustomerResponses.toString()).isEqualTo(actualCustomerResponses.toString());
    }

    @Test
    public void givenCustomerId_shouldReturnCustomerOrderResponse(){
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
        //then
        List<CustomerOrderResponse> actualCustomerOrderResponses = customerService.retrieveCustomerOrders(customerId);
        Assertions.assertThat(expectedCustomerOrderResponses.toString()).isEqualTo(actualCustomerOrderResponses.toString());
    }

    @Test
    public void givenCustomerPhone_shouldReturnCustomerOrderResponse(){
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
        //then
        List<CustomerOrderResponse> actualCustomerOrderResponses = customerService.retrieveCustomerOrders(customerId);
        Assertions.assertThat(expectedCustomerOrderResponses.toString()).isEqualTo(actualCustomerOrderResponses.toString());
    }

    @Test
    public void givenCustomerId_shouldReturnCustomerPayments(){
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
        //then
        List<PaymentResponse> actualPaymentResponses = customerService.retrieveCustomerPayments(customerId);
        Assertions.assertThat(expectedPaymentResponses.toString()).isEqualTo(actualPaymentResponses.toString());
    }
}