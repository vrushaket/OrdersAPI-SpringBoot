package com.order.management.order;

import com.order.management.food.FoodRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class OrderCommandLineRunner implements CommandLineRunner {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    FoodRepository foodRepository;

    @Override
    public void run(String... args) throws Exception {
//        Date now = new Date(System.currentTimeMillis());
//        List<Food> foodList = foodRepository.findAllById(Arrays.asList(4l,1l));
//        if(!foodList.isEmpty()){
//            Order order = new Order(1,3,2,now,1,foodList);
//            orderService.addOrder(order);
//        }else{
//            System.out.println("empty");
//        }
    }
}

