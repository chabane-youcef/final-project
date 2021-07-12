package com.chabane.orderserver.dao;

import com.chabane.orderserver.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
    List<Order> findAll();
    List<Order> findAllByClientId(int id);
//    List<Order> findAllByDeliveryManId(int id);
    Order findById(int id);
    void deleteById(int id);

//    @Query("SELECT o.deliveryManId FROM Order AS o GROUP BY o.deliveryManId ORDER BY COUNT(o.deliveryManId) ASC")
//    List<Integer> countTotalOrdersByDeliveryMan();
}
