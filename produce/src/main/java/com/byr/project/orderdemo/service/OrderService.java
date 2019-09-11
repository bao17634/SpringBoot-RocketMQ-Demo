package com.byr.project.orderdemo.service;

import com.byr.project.orderdemo.dto.OrderDTO;
import com.byr.project.orderdemo.entity.Order;

public interface OrderService {
    Integer saveOrder(OrderDTO orderDTO);
}
