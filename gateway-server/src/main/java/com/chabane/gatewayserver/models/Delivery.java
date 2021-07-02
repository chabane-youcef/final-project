package com.chabane.gatewayserver.models;

import com.chabane.gatewayserver.services.employee.Employee;
import com.chabane.gatewayserver.services.order.Order;
import lombok.Data;

@Data
public class Delivery {
    private String address;
    private Order order;
    private Employee employee;
}
