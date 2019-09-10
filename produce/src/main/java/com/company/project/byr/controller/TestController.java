package com.company.project.byr.controller;

import com.company.project.byr.TransactionProducer;
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
