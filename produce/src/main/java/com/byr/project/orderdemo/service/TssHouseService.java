package com.byr.project.orderdemo.service;

import com.byr.project.orderdemo.dto.OrderDTO;
import com.byr.project.orderdemo.entity.Order;

public interface TssHouseService {
    /**
     * 减少Tss库存
     * @param orderDTO
     * @param transactionId
     * @return
     */
    Integer reduceTssHouse(OrderDTO orderDTO,String transactionId);

    /**
     *
     * @param transactionId
     * @return 检查本地事务是否执行成功
     */
    boolean checkTransferStatus(String transactionId);
}
