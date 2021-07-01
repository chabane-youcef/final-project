package com.chabane.orderserver.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue
    private int id;
    private int productId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
