package com.SimSpot.ecommerce.dto;

import com.SimSpot.ecommerce.entity.Customer;
import com.SimSpot.ecommerce.entity.Address;
import com.SimSpot.ecommerce.entity.Order;
import com.SimSpot.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
