package com.chabane.gatewayserver.services.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetails {
    private int id;
    private int productId;
    private int quantity;
}
