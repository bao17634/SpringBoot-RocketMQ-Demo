package com.byr.project.consumer.demo.serveice;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public interface ConsumerService {
    ConsumeConcurrentlyStatus consumePayMessage(List<MessageExt> messages);
    ConsumeConcurrentlyStatus consumeOrderMessage(List<MessageExt> messages);
}
