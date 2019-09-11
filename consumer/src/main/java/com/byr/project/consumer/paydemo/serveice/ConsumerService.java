package com.byr.project.consumer.paydemo.serveice;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public interface ConsumerService {
    ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages);
}
