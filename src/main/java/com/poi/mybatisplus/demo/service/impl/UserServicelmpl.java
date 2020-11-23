package com.poi.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poi.mybatisplus.demo.entity.User;
import com.poi.mybatisplus.demo.mapper.UserMapper;
import com.poi.mybatisplus.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户service实现层
 * @Author: songkai
 * @Date: 2020/11/18
 * @Version: 1.0
 */
@Service
public class UserServicelmpl extends ServiceImpl<UserMapper, User> implements UserService {
}
