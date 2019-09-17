package com.byr.project.consumer.demo.serveice;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public interface ConsumerService {
    /**
     * 金额消费者
     * @param messages
     * @return
     */
    ConsumeConcurrentlyStatus consumePayMessage(List<MessageExt> messages);

    /**
     * 订单消费者
     * @param messages
     * @return
     */
    ConsumeConcurrentlyStatus consumeOrderMessage(List<MessageExt> messages);
}
