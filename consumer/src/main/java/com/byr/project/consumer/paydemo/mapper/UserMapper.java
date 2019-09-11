package com.byr.project.consumer.paydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byr.project.consumer.paydemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: yanrong
 * @Date: 2019/9/10 17:31
 * @Version: 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int reduceMoney(@Param("userId") Long userId, @Param("money") Long money);
    int addMoney(@Param("userId") Long userId, @Param("money") Long money);
}
