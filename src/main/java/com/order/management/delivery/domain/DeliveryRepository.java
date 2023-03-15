package com.order.management.delivery.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
