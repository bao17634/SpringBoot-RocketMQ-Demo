package com.byr.project.consumer.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byr.project.consumer.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: yanrong
 * @Date: 2019/9/10 17:31
 * @Version: 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 减少金额
     * @param userId
     * @param money
     * @return
     */
    int reduceMoney(@Param("userId") Long userId, @Param("money") Long money);

    /**
     * 增加金额
     * @param userId
     * @param money
     * @return
     */
    int addMoney(@Param("userId") Long userId, @Param("money") Long money);
}
