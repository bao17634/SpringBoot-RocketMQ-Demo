package com.byr.project.orderdemo.dto;

import com.byr.project.orderdemo.entity.Order;
import com.byr.project.orderdemo.entity.TssHouse;
import lombok.Data;

/**
 * @ClassName: OrderDTO
 * @Description: TODO
 * @Author: yanrong
 * @Date: 2019/9/10 19:58
 * @Version: 1.0
 */
@Data
public class OrderDTO {
    private Order order;
    private TssHouse tssHouse;
    /**
     *
     */
    private String commodityCode;
}
