package com.byr.project.paydemo.service.impl;

import com.byr.project.paydemo.entity.User;
import com.byr.project.paydemo.mapper.UserMapper;
import com.byr.project.paydemo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: yanrong
 * @Date: 2019/9/10
 * @Version: 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
