package com.ordermgmt.OrderMS.delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {

    //insert into delivery (status) values (?)
    //insert
}
