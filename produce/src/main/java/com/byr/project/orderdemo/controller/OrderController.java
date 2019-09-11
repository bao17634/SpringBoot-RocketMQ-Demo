package com.byr.project.orderdemo.controller;

import com.byr.project.orderdemo.dto.OrderDTO;
import com.byr.project.orderdemo.entity.Order;
import com.byr.project.orderdemo.service.OrderProduceService;
import com.byr.project.orderdemo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: OrderController
 * @Description: TODO
 * @Author: yanrong
 * @Date: 2019/9/11 11:50
 * @Version: 1.0
 */
@RestController
@Slf4j
@RequestMapping(value = "orderController")
public class OrderController {
    @Autowired
    OrderProduceService orderProduceService;
    @RequestMapping(value = "/addOrder")
    public void addOrder() throws Exception {
        UUID uuid = UUID.randomUUID();
        String code = uuid.toString().replace("-", "");
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();
        order.setOrderCode(code.substring(4)+new Date().toString());
        order.setOrderName("电脑");
        order.setCommodityCode(code);
        order.setOrderCount(10);
        orderDTO.setOrder(order);
        orderProduceService.produceOrder(orderDTO);
    }
}
