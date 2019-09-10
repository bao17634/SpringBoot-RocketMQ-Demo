package com.company.project.byr.service.impl;

import com.company.project.byr.entity.User;
import com.company.project.byr.mapper.UserMapper;
import com.company.project.byr.service.UserService;
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
