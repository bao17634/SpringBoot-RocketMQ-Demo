package com.byr.project.consumer.demo.serveice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.byr.project.consumer.demo.entity.Order;
import com.byr.project.consumer.demo.entity.SaasHouse;
import com.byr.project.consumer.demo.entity.SaasHouseExample;
import com.byr.project.consumer.demo.mapper.OrderMapper;
import com.byr.project.consumer.demo.mapper.SaasHouseMapper;
import com.byr.project.consumer.demo.mapper.UserMapper;
import com.byr.project.consumer.demo.serveice.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: ConsumerServiceImpl
 * @Description: 消费端执行业务
 * @Author: yanrong
 * @Date: 2019/9/10 17:31
 * @Version: 1.0
 */
@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SaasHouseMapper saasHouseMapper;

    @Override
    public ConsumeConcurrentlyStatus consumePayMessage(List<MessageExt> messages) {
        int number = 0;
        try {
            for (MessageExt msg : messages) {
                JSONObject jsonInfo = JSON.parseObject(new String(msg.getBody()));
                Long money = Long.valueOf(jsonInfo.get("changeMoney").toString());
                Long userId = Long.valueOf(jsonInfo.get("toUserId").toString());
                number += userMapper.addMoney(userId, money);
            }
            log.info("成功接收消息：" + System.currentTimeMillis());
            if (number != messages.size()) {
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("消费端发生异常", e);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    //订单消息消费
    @Transactional
    @Override
    public synchronized ConsumeConcurrentlyStatus consumeOrderMessage(List<MessageExt> messages) {
        try {
            for (MessageExt msg : messages) {
                JSONObject jsonInfo = JSON.parseObject(new String(msg.getBody()));
                if (jsonInfo == null || jsonInfo.get("order") == null) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                //通过记录表查看消息是否已经被消费。
                boolean msgStatus = this.checkTransferStatus(msg.getTransactionId());
                if (msgStatus) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                this.saveOrder(jsonInfo, msg.getTransactionId());
                return this.singleConsumerMessage(jsonInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException("消费端发生异常", e);
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }

    public ConsumeConcurrentlyStatus singleConsumerMessage(JSONObject jsonInfo) {
        int saasCunt = 0;
        try {
            JSONObject order = JSON.parseObject(jsonInfo.get("order").toString());
            SaasHouseExample example = new SaasHouseExample();
            example.createCriteria().andCommodityCodeEqualTo(order.get("commodityCode").toString());
            Integer number = saasHouseMapper.selectNumber(order.get("commodityCode").toString());
            SaasHouse saasHouse = new SaasHouse();
            //如果Tss仓库没有这个商品的数据则添加，否则就修改
            if (number == null) {
                saasHouse.setCommodityCode(order.get("commodityCode").toString());
                saasHouse.setCommodityName(order.get("orderName").toString());
                saasHouse.setNumber(Integer.valueOf(order.get("orderCount").toString()));
                saasCunt = saasHouseMapper.insert(saasHouse);
            } else {
                saasHouse.setNumber(Integer.valueOf(order.get("orderCount").toString()) + number);
                saasCunt = saasHouseMapper.updateByExampleSelective(saasHouse, example);
            }
            if (saasCunt == 1) {
                log.info("消息消费成功：" + System.currentTimeMillis());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } else {
                log.info("消息消费失败：" + System.currentTimeMillis());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        } catch (Exception e) {
            throw new RuntimeException("消费消费失败", e);
        }
    }

    /**
     * 将收到的消息插入消息记录表中
     *
     * @param jsonInfo
     * @return
     */
    public void saveOrder(JSONObject jsonInfo, String transactionId) {
        JSONObject orderJson = JSON.parseObject(jsonInfo.get("order").toString());
        Order order = new Order();
        order.setCommodityCode(orderJson.getString("commodityCode"));
        order.setOrderCode(orderJson.getString("orderCode"));
        order.setOrderCount(Integer.valueOf(orderJson.get("orderCount").toString()));
        order.setOrderName(orderJson.getString("orderName"));
        order.setTransactionId(transactionId);
        Integer a = orderMapper.insert(order);
        if (a != 1) {
            throw new RuntimeException("消息消费失败");
        }
    }

    /**
     * 检查本地事务执行状态
     *
     * @param transactionId
     * @return
     */
    public boolean checkTransferStatus(String transactionId) {
        //根据本地线程Id是否有这个数据，有运单记录，标识本地事务执行成功
        try {
            Long count = orderMapper.selectCount(transactionId);
            return count > 0;
        } catch (Exception e) {
         throw new RuntimeException("消息检查回调失败");
        }
    }

}
