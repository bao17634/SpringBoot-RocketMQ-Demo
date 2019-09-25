package com.byr.project.paydemo.controller;

import com.byr.project.paydemo.service.TransactionProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yanrong
 * @Date: 2019/9/10
 * @Version: 1.0
 */
@RestController
@RequestMapping("/testController")
public class TestController {
    @Resource
    private TransactionProducer transactionProducer;

    @RequestMapping("/mqTest")
    public String callback(String data) {
        transactionProducer.test();
        return "Ok";
    }

}
