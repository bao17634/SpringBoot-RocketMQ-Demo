package com.byr.project.orderdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.byr.project.orderdemo.dto.OrderDTO;
import com.byr.project.orderdemo.entity.TssHouse;
import com.byr.project.orderdemo.service.OrderProduceService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * @ClassName: orderProduceServiceImpl
 * @Description: 执行本地事务
 * @Author: yanrong
 * @Date: 2019/9/10 18:55
 * @Version: 1.0
 */
@Service
public  class OrderProduceServiceImpl implements OrderProduceService,InitializingBean {
    @Autowired
    ProduceTransactionListenerImpl transactionListener;
    private static TransactionMQProducer producer = new TransactionMQProducer("orderDemo");
    /**
     * 本方法会在第一条消息发出去后执行本地事务
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        producer.setNamesrvAddr("127.0.0.1:9876");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-message-check-thread");
                return thread;
            }
        });
        //开启多线程，用于回查
        producer.setExecutorService(executorService);
        //设置回调事务检查监听器
        producer.setTransactionListener(transactionListener);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     *向消费端发消息
     */
    @Override
    public void produceOrder(OrderDTO orderDTO) {
        try {
            Message msg = new Message("orderMessage", "order",orderDTO.getOrder().getOrderCode(),
                    JSON.toJSONString(orderDTO).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //生产者通过sendMessageInTransaction想消费者发送事务消息
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            System.out.println("prepare事务消息发送结果:"+sendResult.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
