package com.company.project.consumer;

import com.company.project.consumer.serveice.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: ConsumerUtil
 * @Description: springboot启动后初始化加载
 * @Author: yanrong
 * @Date: 2019/9/10 17:24
 * @Version: 1.0
 */
@Component
@Slf4j
public class ConsumerUtil implements CommandLineRunner {
    @Autowired
    ConsumerService  consumerService;
    @Override
    public void run(String... strings) throws Exception {
        /*
         * Instantiate with specified consumer group name.
         * 实例化组名称，提供消费者对象
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_name");
        /*
         * Specify name server addresses.
         * <p/>
         * Alternatively, you may specify name server addresses via exporting environmental variable: NAMESRV_ADDR
         * <pre>
         * {@code
         * consumer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
         * }
         * </pre>
         */
        consumer.setNamesrvAddr("127.0.0.1:9876");
        /*
         * Specify where to start in case the specified consumer group is a brand new one.
         * 为防止指定的消费组是一个新的消费组，所以指定从何处开始
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        /*
         * Subscribe one more more topics to consume.
         * 订阅一个要使用的主题
         */
        consumer.subscribe("TransanctionMessage", "*");
        /*
         *  Register callback to execute on arrival of messages fetched from brokers.
         *  注册回调函数，以便在从代理获取的消息到达时执行
         */
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> message, ConsumeConcurrentlyContext context) {
                ConsumeConcurrentlyStatus concurrentlyStatus=consumerService.consumeMessage(message);
                log.info(concurrentlyStatus.toString());
                return concurrentlyStatus;
            }
        });
        /*
         *  Launch the consumer instance.
         *  启动使用者实例
         */
        consumer.start();
        log.info("success！consumer started.");
    }
}
