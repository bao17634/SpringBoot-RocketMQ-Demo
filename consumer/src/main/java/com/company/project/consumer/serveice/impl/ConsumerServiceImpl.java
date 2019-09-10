package com.company.project.consumer.serveice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.project.consumer.mapper.UserMapper;
import com.company.project.consumer.serveice.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages) {
        int number = 0;
        try {
            for (MessageExt msg : messages) {
                JSONObject jsonInfo = JSON.parseObject(new String(msg.getBody()));
                Long money=Long.valueOf(jsonInfo.get("changeMoney").toString());
                Long userId=Long.valueOf(jsonInfo.get("toUserId").toString());
               number+= userMapper.addMoney(userId,money);
            }
            log.info("成功接收消息："+System.currentTimeMillis());
           if (number != messages.size()) {
               return ConsumeConcurrentlyStatus.RECONSUME_LATER;
           }
        }catch (Exception e){
            log.error(e.toString());
            throw new RuntimeException("消费端发生异常",e);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
