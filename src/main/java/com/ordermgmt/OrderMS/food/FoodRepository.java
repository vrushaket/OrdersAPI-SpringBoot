package com.ordermgmt.OrderMS.food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {

    //insert into food (name, price) values (?, ?)
    //save

    //delete from food where id=?
    //delete

    //select f1_0.id,f1_0.name,f1_0.price from food f1_0 where f1_0.id=?
    //findById

    //select f1_0.id,f1_0.name,f1_0.price from food f1_0
    //findAll

    //update food set name=?, price=? where id=?
    //update
}
