package com.chabane.orderserver.models;

import com.chabane.orderserver.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private int id;

    private int clientId;
    private int deliveryManId;
    private String address;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @JsonIgnoreProperties("order")
    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails;

}
