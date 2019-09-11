package com.byr.project.orderdemo.service;

import com.byr.project.orderdemo.dto.OrderDTO;

public interface TssHouseService {
    Integer reduceTssHouse(OrderDTO orderDTO);
    boolean checkTransferStatus(String transactionId);
}
