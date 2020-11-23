package com.poi.mybatisplus.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.poi.mybatisplus.demo.entity.User;
import com.poi.mybatisplus.demo.mapper.UserMapper;
import com.poi.mybatisplus.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {

    @Autowired
    private UserService userService;

    /**
     selectOne
     */
    @Test
    public void selectOne(){
        //只能有一个 多个报错
        /*User one =userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge,25));
        System.out.println(one);*/

        //多个返回第一个
        User two =userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge,25),false);
        System.out.println(two);
    }
/*
报错 因为查到6个,
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age > ?)
==> Parameters: 25(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20
<==        Row: 1088250446457389058, 李艺伟, 32, lyw2019@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==        Row: 1328175613846011906, 刘花, 29, Ih@baomidou.com, 1088248166370832385, 2020-11-16 11:18:37
<==        Row: 1328176061785092098, 刘花-1, 28, null, 1088248166370832385, 2020-11-16 11:20:24
<==      Total: 6
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@453d496b]

org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 6

第二句正常
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age > ?)
==> Parameters: 25(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20
<==        Row: 1088250446457389058, 李艺伟, 32, lyw2019@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==        Row: 1328175613846011906, 刘花, 29, Ih@baomidou.com, 1088248166370832385, 2020-11-16 11:18:37
<==        Row: 1328176061785092098, 刘花-1, 28, null, 1088248166370832385, 2020-11-16 11:20:24
<==      Total: 6
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@34d713a2]
2020-11-18 15:38:10.086  WARN 47628 --- [           main] c.p.m.demo.service.impl.UserServicelmpl  : Warn: execute Method There are  6 results.
User(id=1087982257332887553, Name=大boss, age=40, email=boss@baomidou.com, managerId=null, createTime=2019-01-11T14:20:20)


*/
    //TODO 批量操作方法
    /**
     新增
    */
    @Test
    public void saveBatch(){
        User user1=new User();
        user1.setName("徐丽1");
        user1.setAge(28);
        User user2=new User();
        user2.setName("徐丽2");
        user2.setAge(29);
        List<User> userList=Arrays.asList(user1,user2);
        boolean saveBatch =userService.saveBatch(userList);
        System.out.println(saveBatch);
    }
/*
==>  Preparing: INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
==> Parameters: 1328967026484281346(Long), 徐丽1(String), 28(Integer)
==> Parameters: 1328967026610110466(Long), 徐丽2(String), 29(Integer)
true
*/

    /**
     新增或更新
     存在主键的情况下 会先查询

     */
    @Test
    public void saveOrUpdateBatch(){
        User user1=new User();
        user1.setName("徐丽3");
        user1.setAge(28);
        User user2=new User();
        user2.setName("徐丽4");
        user2.setAge(29);
        user2.setId(1104701623129399297L);
        List<User> userList=Arrays.asList(user1,user2);
        boolean saveBatch =userService.saveOrUpdateBatch(userList);
        System.out.println(saveBatch);
    }
/*
一条插入一条查询一条更新
==>  Preparing: INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
==> Parameters: 1328968912213987329(Long), 徐丽3(String), 28(Integer)
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE id=?
==> Parameters: 1104701623129399297(Long)
<==      Total: 0
==>  Preparing: INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
==> Parameters: 1104701623129399297(Long), 徐丽4(String), 29(Integer)
true
*/
    //TODO 链式调用
    /**
     查询
     */
    @Test
    public void lambda(){
        List<User> userList =userService.lambdaQuery().gt(User::getAge,25).like(User::getName, "雨").list();
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age > ? AND name LIKE ?)
==> Parameters: 25(Integer), %雨%(String)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==      Total: 2
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4b54af3d]
User(id=1094590409767661570, Name=张雨琪, age=31, email=zjq@baomidou.com, managerId=1088248166370832385, createTime=2019-01-14T09:15:15)
User(id=1094592041087729666, Name=刘红雨, age=32, email=lhm@baomidou.com, managerId=1088248166370832385, createTime=2019-01-14T09:48:16)
*/
    /**
     更新
     */
    @Test
    public void lambda2(){
        //25岁改为26岁
        boolean flg =userService.lambdaUpdate().eq(User::getAge,25).set(User::getAge,26).update();
        System.out.println(flg);
    }
/*
==>  Preparing: UPDATE user SET age=? WHERE (age = ?)
==> Parameters: 26(Integer), 25(Integer)
<==    Updates: 2
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3e1624c7]
true
*/

    /**
     删除
     */
    @Test
    public void lambda3(){
        //25岁改为26岁
        boolean flg =userService.lambdaUpdate().eq(User::getAge,25).remove();
        System.out.println(flg);
    }
/*

*/



}
