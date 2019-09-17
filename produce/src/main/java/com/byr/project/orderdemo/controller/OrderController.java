package com.byr.project.orderdemo.controller;

import com.byr.project.orderdemo.dto.OrderDTO;
import com.byr.project.orderdemo.entity.Order;
import com.byr.project.orderdemo.service.OrderProduceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
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
    public Integer addOrder() throws Exception {
        String code = UUID.randomUUID().toString().replace("-", "");
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();
        order.setOrderCode(code.substring(4)+date);
        order.setOrderName("电脑");
        order.setCommodityCode("bf5f48fcfa9d43bca391e9f66fd8c9ba");
        order.setOrderCount(10);
        orderDTO.setOrder(order);
        orderProduceService.produceOrder(orderDTO);
        return 1;
    }
}
