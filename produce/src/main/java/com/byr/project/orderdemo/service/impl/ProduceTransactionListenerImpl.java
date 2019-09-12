/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.byr.project.orderdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byr.project.orderdemo.dto.OrderDTO;
import com.byr.project.orderdemo.service.TssHouseService;
import com.byr.project.paydemo.entity.TransferRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc: 执行本地事务
 * @Author: yanrong
 * @DATE： 2019/9/10
 */

@Service
@Slf4j
public class ProduceTransactionListenerImpl implements TransactionListener {
    @Autowired
    TssHouseService tssHouseService;

    /**
     * 本地事务方法，这个方法会在每一条消息发出去后执行，保证事务的一致。
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        OrderDTO orderDTO = JSON.parseObject(msg.getBody(), OrderDTO.class);
        //设置该消息
        LocalTransactionState state = LocalTransactionState.UNKNOW;
        try {
            Integer isCommit = tssHouseService.reduceTssHouse(orderDTO,msg.getTransactionId());
            if (isCommit == 1) {
                state = LocalTransactionState.COMMIT_MESSAGE;
            } else {
                state = LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("转账成功：" + System.currentTimeMillis());
        return state;
    }

    /**
     * 每隔一段时间  rocketMQ 会回调 这个方法 判断 每一条消息是否提交。防止 消息状态停滞 或者出现超时的情况
     *
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        LocalTransactionState state = LocalTransactionState.UNKNOW;
        try {
            OrderDTO orderDTO = JSON.parseObject(msg.getBody(), OrderDTO.class);
            if (orderDTO != null) {
                state = LocalTransactionState.ROLLBACK_MESSAGE;
            }
            log.info("消费端异常调用" + msg.getTransactionId());
            boolean isCommit = tssHouseService.checkTransferStatus(msg.getTransactionId());
            if (isCommit){
                state = LocalTransactionState.COMMIT_MESSAGE;
            }else {
                state = LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }
}
