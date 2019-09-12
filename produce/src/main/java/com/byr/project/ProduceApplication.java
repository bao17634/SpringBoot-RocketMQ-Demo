package com.byr.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring/*.xml"})
//扫描project下面所有的mapper文件，这里必须要加@MapperScan不然会报mapper文件找不到的错误
@MapperScan({"com.byr.project.paydemo.mapper","com.byr.project.orderdemo.mapper"})
public class ProduceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProduceApplication.class, args);
    }
}

