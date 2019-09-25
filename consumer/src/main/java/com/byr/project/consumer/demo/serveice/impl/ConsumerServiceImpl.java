package com.byr.project.consumer.demo.serveice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.byr.project.consumer.demo.entity.Order;
import com.byr.project.consumer.demo.entity.SaasHouse;
import com.byr.project.consumer.demo.entity.SaasHouseExample;
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

    @Override
    public synchronized ConsumeConcurrentlyStatus consumeOrderMessage(List<MessageExt> messages) {
        try {
            for (MessageExt msg : messages) {
                return this.singleConsumerMessage(msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("消费端发生异常", e);
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
    @Transactional
    public ConsumeConcurrentlyStatus singleConsumerMessage(MessageExt msg){
        int  saasCunt=0;
       try {
           JSONObject jsonInfo = JSON.parseObject(new String(msg.getBody()));
           if (jsonInfo == null || jsonInfo.get("order") == null) {
               return ConsumeConcurrentlyStatus.RECONSUME_LATER;
           }
           JSONObject order = JSON.parseObject(jsonInfo.get("order").toString());
           SaasHouseExample example = new SaasHouseExample();
           example.createCriteria().andCommodityCodeEqualTo(order.get("commodityCode").toString());
           Integer number = saasHouseMapper.selectNumber(order.get("commodityCode").toString());
           SaasHouse saasHouse = new SaasHouse();
           //如果Tss仓库没有这个商品的数据则添加，否则就修改
           if (number == null ) {
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
           }else {
               log.info("消息消费失败：" + System.currentTimeMillis());
               return ConsumeConcurrentlyStatus.RECONSUME_LATER;
           }
       }catch (Exception e){
           throw new RuntimeException("消费消费失败",e);
       }
    }

}
