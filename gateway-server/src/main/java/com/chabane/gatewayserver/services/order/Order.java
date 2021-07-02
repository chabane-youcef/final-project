package com.chabane.gatewayserver.services.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private int id;
    private int clientId;

    private Date orderDate;

    private OrderStatus status;
    private List<OrderDetails> orderDetails;

}
