package com.byr.project.orderdemo.service.impl;

import com.byr.project.orderdemo.dto.OrderDTO;
import com.byr.project.orderdemo.mapper.OrderMapper;
import com.byr.project.orderdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: orderServiceImpl
 * @Description: TODO
 * @Author: yanrong
 * @Date: 2019/9/10 19:32
 * @Version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Override
    public Integer saveOrder(OrderDTO orderDTO) {
        return orderMapper.insert(orderDTO.getOrder());
    }
}
