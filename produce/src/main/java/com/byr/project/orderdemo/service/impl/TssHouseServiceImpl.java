package com.byr.project.orderdemo.service.impl;

import com.byr.project.orderdemo.dto.OrderDTO;
import com.byr.project.orderdemo.entity.Order;
import com.byr.project.orderdemo.entity.OrderExample;
import com.byr.project.orderdemo.entity.TssHouse;
import com.byr.project.orderdemo.entity.TssHouseExample;
import com.byr.project.orderdemo.mapper.OrderMapper;
import com.byr.project.orderdemo.mapper.TssHouseMapper;
import com.byr.project.orderdemo.service.TssHouseService;
import com.byr.project.paydemo.entity.TransferRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: TssHouseServiceImpl
 * @Description: TODO
 * @Author: yanrong
 * @Date: 2019/9/10 19:38
 * @Version: 1.0
 */
@Service
@Slf4j
public class TssHouseServiceImpl implements TssHouseService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    TssHouseMapper tssHouseMapper;
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public synchronized Integer reduceTssHouse(OrderDTO orderDTO,String transactionId) {
//        将本地事务Id存入到运单表中，以便后续检验本地事务是否执行成功
        orderDTO.getOrder().setTransactionId(transactionId);
        Integer orderCount=orderMapper.insert(orderDTO.getOrder());
        if(orderCount != 1){
            log.error("订单表插入失败");
            throw new RuntimeException("订单表插入失败");
        }
        TssHouseExample example = new TssHouseExample();
        TssHouse tssHouse=new TssHouse();
        example.createCriteria().andCommodityCodeEqualTo(orderDTO.getOrder().getCommodityCode());
        List<TssHouse> listTss = tssHouseMapper.selectByExample(example);
        if (listTss == null || listTss.size() < 1) {
            log.error("TSS没有此商品的库存");
            throw new RuntimeException("TSS没有此商品的库存");
        }else if (listTss.get(0).getNumber() < orderDTO.getOrder().getOrderCount()) {
            log.error("此商品在Tss库存不足");
            throw new RuntimeException("此商品在Tss库存不足");
        }
        tssHouse.setNumber(listTss.get(0).getNumber() - orderDTO.getOrder().getOrderCount());
        Integer a = tssHouseMapper.updateByExampleSelective(tssHouse, example);
        return a;
    }
    /**
     * 检查本地扣钱事务执行状态
     * @param transactionId
     * @return
     */
    public boolean checkTransferStatus(String transactionId) {
        //根据本地线程Id是否有这个数据，有运单记录，标识本地事务执行成功
        OrderExample example=new OrderExample();
        example.createCriteria().andTransactionIdEqualTo(transactionId);
       System.out.println( transactionId);
        Long count = orderMapper.countByExample(example);
        return count > 0;

    }
}
