package com.chabane.orderserver.controllers;

import com.chabane.orderserver.dao.OrderDao;
import com.chabane.orderserver.dao.OrderDetailsDao;
import com.chabane.orderserver.enums.OrderStatus;
import com.chabane.orderserver.models.Order;
import com.chabane.orderserver.models.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailsDao orderDetailsDao;

    //TODO: refactor functions to gateway server
//    @GetMapping(value = "orders")
//    public List<Order> index() {
//        List<Order> orders = orderDao.findAll();
//        // Get Orders Products from products service
//        for (Order o : orders) {
//            for(OrderDetails od: o.getOrderDetails()) {
//                od.setProduct(productProxy.getProduct(od.getProductId()));
//            }
//        }
//        return orders;
//    }
//
//    @GetMapping(value = "client-orders/{id}")
//    public List<Order> clientOrders(@PathVariable("id") int clientId) {
//
//        List<Order> orders = orderDao.findAllByClientId(clientId);
//
//        for (Order o : orders) {
//            for(OrderDetails od: o.getOrderDetails()) {
//                od.setProduct(productProxy.getProduct(od.getProductId()));
//            }
//        }
//
//        return orders;
//    }
//
    @GetMapping(value = "/orders/{id}")
    public Order getOrder(@PathVariable("id") int id) {
        Order order = orderDao.findById(id);

//        for(OrderDetails details: order.getOrderDetails()) {
//            details.setProduct(productProxy.getProduct(details.getProductId()));
//        }
//        System.out.println(order);
        return order;
    }
//
//    private int getDeliveryManId() {
//
//        // all delivery man order by top delivery man
//        List<Integer> delivery = orderDao.countTotalOrdersByDeliveryMan();
//
//        List<Employee> allEmployees = employeeProxy.getAllDeliveryMen();
//
//        for (Employee employee: allEmployees) {
//            if(!delivery.contains(employee.getId())) {
//                return employee.getId();
//            }
//        }
//        return delivery.get(0);
//    }

    @PostMapping(value = "orders")
    public Order store(@RequestBody Order order) {

        System.out.println(order);
        Date date = new Date(System.currentTimeMillis());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(date);
//        order.setDeliveryManId(getDeliveryManId());
        order = orderDao.save(order);

        return storeOrderDetails(order, order.getOrderDetails());
    }

    private Order storeOrderDetails(Order order, List<OrderDetails> details) {

        Order orderCopy = new Order();
        orderCopy.setId(order.getId());
//        orderCopy.setAddress(order.getAddress());
        orderCopy.setOrderDate(order.getOrderDate());
        orderCopy.setStatus(order.getStatus());

        for(OrderDetails od: details) {
            od.setOrder(orderCopy);
        }
        details = orderDetailsDao.saveAll(details);
        orderCopy.setOrderDetails(details);

        return orderCopy;
    }

    @GetMapping(value = "next-status/{id}")
    public Order nextStatus(@PathVariable("id") int id) {
        Order order = orderDao.findById(id);
        switch (order.getStatus()) {
            case PENDING:
                order.setStatus(OrderStatus.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                order.setStatus(OrderStatus.FINISHED);
                break;
        }

        orderDao.save(order);

        return order;
    }

    @GetMapping(value = "refuse-order/{id}")
    public Order refuseOrder(@PathVariable("id") int id) {
        Order order = orderDao.findById(id);
        order.setStatus(OrderStatus.REFUSED);
        orderDao.save(order);

        return order;
    }
}
