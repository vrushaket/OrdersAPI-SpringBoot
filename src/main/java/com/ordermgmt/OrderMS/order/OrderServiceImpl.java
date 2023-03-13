package com.ordermgmt.OrderMS.order;

import com.ordermgmt.OrderMS.customer.Customer;
import com.ordermgmt.OrderMS.delivery.Delivery;
import com.ordermgmt.OrderMS.food.Food;
import com.ordermgmt.OrderMS.food.FoodRepository;
import com.ordermgmt.OrderMS.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository repository;

    @Autowired
    FoodRepository foodRepository;

    public void addOrder(Order order) {
        List<Food> foodList = new ArrayList<>();
        for (Food food : order.getFoods()) {
            Optional<Food> optionalFood = foodRepository.findById(food.getId());
            foodList.add(optionalFood.get());
        }
        order.setFoods(foodList);
        repository.save(order);
    }

    public void deleteOrder(long orderId) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (optionalOrder.isPresent()) {
            repository.deleteById(orderId);
        }
    }

    public void updateOrder(long orderId, Order order) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();
            existingOrder.setDate(order.getDate());
            existingOrder.setCustomerId(order.getCustomerId());
            existingOrder.setDeliveryId(order.getDeliveryId());
            existingOrder.setPaymentId(order.getPaymentId());
            existingOrder.setQuantity(order.getQuantity());
            repository.save(existingOrder);
        }
    }

    public Order retrieveSpecificOrder(long orderId) {
        Optional<Order> order = repository.findById(orderId);
        if(order.isEmpty()) return null;
        return order.get();
    }

    public List<Order> retrieveAllOrder() {
        List<Order> allOrder = repository.findAll();
        if(allOrder.isEmpty()) return null;
        return allOrder;
    }


    public Payment retrieveOrderPayment(long orderId) {
        return repository.findPaymentByOrderId(orderId);
    }

    public Delivery retrieveOrderDelivery(long orderId) {
        return repository.findDeliveryByOrderId(orderId);
    }
    public List<Food> retrieveOrderFood(long orderId) {
        return repository.findFoodByOrderId(orderId);
    }
    public List<Customer> retrieveOrderCustomer(long orderId) {
        return repository.findCustomerByOrderId(orderId);
    }

}
