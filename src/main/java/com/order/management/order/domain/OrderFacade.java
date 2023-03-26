package com.order.management.order.domain;

import com.order.management.customer.domain.Customer;
import com.order.management.delivery.domain.Delivery;
import com.order.management.food.domain.Food;
import com.order.management.food.domain.FoodRepository;
import com.order.management.order.api.request.OrderRequest;
import com.order.management.order.exception.OrderNotFoundException;
import com.order.management.payment.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderFacade {

    private final OrderRepository repository;
    private final FoodRepository foodRepository;

    @Autowired
    public OrderFacade(OrderRepository repository, FoodRepository foodRepository) {
        this.repository = repository;
        this.foodRepository = foodRepository;
    }

    public Order addOrder(OrderRequest orderRequest) {
        Order order = new Order(orderRequest.getCustomerId(),orderRequest.getPaymentId(),orderRequest.getDeliveryId(),orderRequest.getDate(),orderRequest.getQuantity(),orderRequest.getFoods());
        List<Food> foodList = new ArrayList<>();
        for (Food food : order.getFoods()) {
            Optional<Food> optionalFood = foodRepository.findById(food.getId());
            foodList.add(optionalFood.get());
        }
        order.setFoods(foodList);
        return repository.save(order);
    }

    public Order deleteOrder(long orderId) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (!optionalOrder.isPresent()) throw new OrderNotFoundException("id : "+orderId);
        repository.deleteById(orderId);
        return optionalOrder.get();
    }

    public Order updateOrder(long orderId, OrderRequest orderRequest) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (!optionalOrder.isPresent()) throw new OrderNotFoundException("id : "+orderId);
        Order existingOrder = optionalOrder.get();
        existingOrder.setDate(orderRequest.getDate());
        existingOrder.setCustomerId(orderRequest.getCustomerId());
        existingOrder.setDeliveryId(orderRequest.getDeliveryId());
        existingOrder.setPaymentId(orderRequest.getPaymentId());
        existingOrder.setQuantity(orderRequest.getQuantity());
        return repository.save(existingOrder);
    }

    public Order retrieveSpecificOrder(long orderId) {
        Optional<Order> order = repository.findById(orderId);
        if(order.isEmpty()) throw new OrderNotFoundException("id : "+orderId);
        return order.get();
    }

    public List<Order> retrieveAllOrder() {
        List<Order> allOrder = repository.findAll();
        if(allOrder.isEmpty()) throw new OrderNotFoundException();
        return allOrder;
    }


    public Payment retrieveOrderPayment(long orderId) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (!optionalOrder.isPresent()) throw new OrderNotFoundException("id : "+orderId);
        return repository.findPaymentByOrderId(orderId);
    }

    public Delivery retrieveOrderDelivery(long orderId) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (!optionalOrder.isPresent()) throw new OrderNotFoundException("id : "+orderId);
        return repository.findDeliveryByOrderId(orderId);
    }

    public List<Food> retrieveOrderFood(long orderId) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (!optionalOrder.isPresent()) throw new OrderNotFoundException("id : "+orderId);
        return repository.findFoodByOrderId(orderId);
    }

    public Customer retrieveOrderCustomer(long orderId) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (!optionalOrder.isPresent()) throw new OrderNotFoundException("id : "+orderId);
        return repository.findCustomerByOrderId(orderId);
    }
}
