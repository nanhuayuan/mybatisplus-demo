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
public class SampleTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = mapper.selectList(null);
        //Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    /**1、名字中包含雨并且年龄小于40
     name like '%雨%' and age<40
     */
    @Test
    public void selectByWrapper(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        //QueryWrapper<User>query =Wrappers.<User>query)
        queryWrapper.like("name","雨").lt("age",40);

        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*    ==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND age < ?)
      ==> Parameters: %雨%(String), 40(Integer)
      <==    Columns: id, name, age, email, manager_id, create_time
      <==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
      <==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
      <==      Total: 2*/

    /*2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
    name like '%雨%' and age between 20 and 40 and email is not nul*/
    @Test
    public void selectByWrapper2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("name","雨").between("age",20,40).isNotNull("email");

        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
==> Parameters: %雨%(String), 20(Integer), 40(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==      Total: 2*/

    /***3、名字为王姓或者年龄大于等于25,按照年龄降序排列,年龄相同按照id升序排列
     name like'王%' or age>=25 order by age desc,id asc
     */
    @Test
    public void selectByWrapper3(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.likeRight("name","雨").or().gt("age",25).orderByDesc("age").orderByAsc("id");

        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? OR age > ?) ORDER BY age DESC,id ASC
==> Parameters: 雨%(String), 25(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==      Total: 4
* */

    /**
     *4.创建日期为2019年2月14日并且直属上级为名字为王姓
     date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
     */
    @Test
    public void selectByWrapper4(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        //--------------2个参数 后面的会直接当作参数替代---------------
        /*queryWrapper.apply("date_format(create_time,'%Y-%m-%d')= {0}", "2019-02-14")
                .inSql("manager_id", "select id from user where name like '王%'");*/
        /*queryWrapper.apply("date_format(create_time,'%Y-%m-%d')= {0}", "2019-02-14 or true or true")
                .inSql("manager_id", "select id from user where name like '王%'");*/

        //--------------1个参数 整体当作sql---------------
        /*queryWrapper.apply("date_format(create_time,'%Y-%m-%d')= '2019-02-14'")
                .inSql("manager_id", "select id from user where name like '王%'");*/

        //查询出所有 相当于sql注入
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')= '2019-02-14' or true or true")
                .inSql("manager_id", "select id from user where name like '王%'");
        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (date_format(create_time,'%Y-%m-%d')= ? AND manager_id IN (select id from user where name like '王%'))
==> Parameters: 2019-02-14(String)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==      Total: 1

对比上下两个sql,上面使用了占位符,可以防止sql注入

==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (date_format(create_time,'%Y-%m-%d')= '2019-02-14' AND manager_id IN (select id from user where name like '王%'))
==> Parameters:
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==      Total: 1

最后sql注入了
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (date_format(create_time,'%Y-%m-%d')= '2019-02-14' or true or true AND manager_id IN (select id from user where name like '王%'))
==> Parameters:
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20
<==        Row: 1088248166370832385, 王天风, 25, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==      Total: 5
* */


    /**5、名字为王姓并且(年龄小于40或邮箱不为空)
     name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectByWrapper5(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.likeRight("name","王").and(wq -> wq.lt("age",40).or().isNotNull("email"));

        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND (age < ? OR email IS NOT NULL))
==> Parameters: 王%(String), 40(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1088248166370832385, 王天风, 25, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22
<==      Total: 1
* */
    /**
     6、名字为王姓或者(年龄小于40并且年龄大于20并且邮箱不为空)
     name like '王%' or (age<40 and age>20 and email is not null)
    */
    @Test
    public void selectByWrapper6(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.likeRight("name","王").or(wq -> wq.lt("age",40).gt("age",20).isNotNull("email"));

        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? OR (age < ? AND age > ? AND email IS NOT NULL))
==> Parameters: 王%(String), 40(Integer), 20(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1088248166370832385, 王天风, 25, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==      Total: 4
* */

    /***7、(年龄小于40或邮箱不为空)并且名字为王姓
     (age<40 or email is not null) and name like '王%'

     */
    @Test
    public void selectByWrapper7(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.nested(wq -> wq.lt("age",40).or().isNotNull("email")).likeRight("name","王");

        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE ((age < ? OR email IS NOT NULL) AND name LIKE ?)
==> Parameters: 40(Integer), 王%(String)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1088248166370832385, 王天风, 25, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22
<==      Total: 1
* */
    /**
     *8、年龄为30、31、34、35
     age in (30、31、34、35)
     */
    @Test
    public void selectByWrapper8(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35));

        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age IN (?,?,?,?))
==> Parameters: 30(Integer), 31(Integer), 34(Integer), 35(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==      Total: 1
* */
    /**9、只返回满足条件的其中一条语句即可
     limit 1
     last 只能调用一次,多次调用以最后一次为准有sq注入的风险,请谨慎使用
     */
    @Test
    public void selectByWrapper9(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");

        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age IN (?,?,?,?)) limit 1
==> Parameters: 30(Integer), 31(Integer), 34(Integer), 35(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==      Total: 1
* */

    /**
     二、select中字段不全部出现的查询
     10、名字中包含雨并且年龄小于40(需求1加强版)
     第一种情况：select id,name
     from user
     where name like '%雨%' and age<40
     第二种情况：select id,name,age,email
     from user
     where name like '%雨%' and age<40
     */
    @Test
    public void selectByWrapper10(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        //queryWrapper.select("id", "name").like("name","雨").lt("age",40);

        queryWrapper.like("name","雨").lt("age",40)
                .select(User.class, info -> info.getColumn().equals("create_time") && info.getColumn().equals("manager_id"));
        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
 ==>  Preparing: SELECT id,name FROM user WHERE (name LIKE ? AND age < ?)
==> Parameters: %雨%(String), 40(Integer)
<==    Columns: id, name
<==        Row: 1094590409767661570, 张雨琪
<==        Row: 1094592041087729666, 刘红雨
<==      Total: 2

==>  Preparing: SELECT id FROM user WHERE (name LIKE ? AND age < ?)
==> Parameters: %雨%(String), 40(Integer)
<==    Columns: id
<==        Row: 1094590409767661570
<==        Row: 1094592041087729666
<==      Total: 2
 */

    /***三、统计查询：
     11、按照直属上级分组,查询每组的平均年龄、最大年龄、最小年龄。
     并且只取年龄总和小于500的组。
     select avg(age) avg_age,min(age) min_age,max(age) max_age
     from user
     group by manager_id
     having sum(age) <500
     */
    @Test
    public void selectByWrapper11(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        //queryWrapper.select("id", "name").like("name","雨").lt("age",40);

        queryWrapper.select("avg(age) avg_age,min(age) min_age,max(age) max_age").groupBy("manager_id").having("sum(age) < {0}", 500);
        List<User> userList = mapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: SELECT avg(age) avg_age,min(age) min_age,max(age) max_age FROM user GROUP BY manager_id HAVING sum(age) < ?
==> Parameters: 500(Integer)
<==    Columns: avg_age, min_age, max_age
<==        Row: 40.0000, 40, 40
<==        Row: 25.0000, 25, 25
<==        Row: 30.3333, 28, 32
<==      Total: 3
 */

    /**
    使用实体类作为查询参数,默认不为null的作为查询条件,构造器默认是等值条件
     如果不是,需要指定
     @TableFieId(conditon = SqlCondition.LIKE) 会左右都有%,可以自己到类里面看
     如果没有,可以用自定义
     @TableField(condition="%s&lt;#{%s}")  ;前面列名,后面列值,生成的条件age<?
     */

    @Test
    public void selectByWrapperCondition(){
        User whereUser = new User();
        whereUser.setName("刘红雨");
        whereUser.setAge(32);
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>(whereUser);
        //下面这个也可以同时生效
        //queryWrapper.like("name","雨").lt("age",40);
        List<User> userList=mapper.selectList(queryWrapper);
    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE name=? AND age<?
==> Parameters: 刘红雨(String), 32(Integer)
<==      Total: 0
 */


    /**
     条件构造器allEq
     */
    @Test
    public void selectByWrapperAll(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        Map<String, Object> params =new HashMap<String,Object>();
        params.put("name","王天风");
        params.put("age",25);
        queryWrapper.allEq(params);
        //queryWrapper.allEq(params,false);
        //也可以加入过滤条件 key为name不参与条件
        //queryWrapper.allEq((k,v)->!k.equals("name"),params);
        List<User> userList = mapper.selectList(queryWrapper);
    }
    /*
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE name=? AND age<?
==> Parameters: 刘红雨(String), 32(Integer)
<==      Total: 0
 */

    /**
     条件构造器Other
     */
    @Test
    public void selectByWrapperOther(){

        QueryWrapper<User> queryWrapper= new QueryWrapper<User>();
        queryWrapper.select("id","name").like("name","雨").lt("age",40);
        //只输出第一列
        List<Object> userList= mapper.selectObjs(queryWrapper);
        userList.forEach(System.out::println);
    }
    /*
/*
==>  Preparing: SELECT id,name FROM user WHERE (name LIKE ? AND age < ?)
==> Parameters: %雨%(String), 40(Integer)
<==    Columns: id, name
<==        Row: 1094590409767661570, 张雨琪
<==        Row: 1094592041087729666, 刘红雨
<==      Total: 2
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@476a736d]
只输出了第一列
1094590409767661570
1094592041087729666
 */

    /**
     查询列数
     */
    @Test
    public void selectByWrapperCount(){

        QueryWrapper<User> queryWrapper= new QueryWrapper<User>();
        //不能指定检索字段
        queryWrapper.like("name","雨").lt("age",40);
        Integer count=mapper.selectCount(queryWrapper);
        System.out.println("总记录数"+count);
    }
    /*
/*
==>  Preparing: SELECT COUNT( 1 ) FROM user WHERE (name LIKE ? AND age < ?)
==> Parameters: %雨%(String), 40(Integer)
<==    Columns: COUNT( 1 )
<==        Row: 2
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fb7183b]
总记录数2
 */

    /**
     只返回一条记录或者0条
     */
    @Test
    public void selectByWrapperCountOne(){

        QueryWrapper<User> queryWrapper= new QueryWrapper<User>();
        //不能指定检索字段
        queryWrapper.like("name","雨").lt("age",40).last("limit 1");
        Integer count=mapper.selectCount(queryWrapper);
        System.out.println("总记录数"+count);
    }
    /*
/*
==>  Preparing: SELECT COUNT( 1 ) FROM user WHERE (name LIKE ? AND age < ?) limit 1
==> Parameters: %雨%(String), 40(Integer)
<==    Columns: COUNT( 1 )
<==        Row: 2
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5a8ba37c]
总记录数2
 */


    /**
     lambda构造器
     */
    @Test
    public void selectByWrapperLambda(){
        //创建lambda的三种方式
        /*LambdaQueryWrapper<User> lambda=new QueryWrapper<User>().lambda();
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<User>();*/
        LambdaQueryWrapper<User> lambdaQuery= Wrappers.<User>lambdaQuery();
        lambdaQuery.like(User::getName, "雨").lt(User::getAge, 40);

        List<User> userList=mapper.selectList(lambdaQuery);

        userList.forEach(System.out::println);
    }
    /*
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND age < ?)
==> Parameters: %雨%(String), 40(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==      Total: 2
 */

    /**
     四种用法1
     5、名字为王姓并且(年龄小于40或邮箱不为空)
     name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectByWrapperLambda1(){
        //创建lambda的三种方式
        /*LambdaQueryWrapper<User> lambda=new QueryWrapper<User>().lambda();
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<User>();*/
        LambdaQueryWrapper<User> lambdaQuery= Wrappers.<User>lambdaQuery();
        lambdaQuery.likeRight(User::getName,"王")
		.and(lqw->lqw.lt(User::getAge,40).or().isNotNull(User::getEmail));

        List<User> userList=mapper.selectList(lambdaQuery);
        userList.forEach(System.out::println);
    }
    /*
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND (age < ? OR email IS NOT NULL))
==> Parameters: 王%(String), 40(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1088248166370832385, 王天风, 25, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22
<==      Total: 1
 */

    /**
     四种用法4
     */
    @Test
    public void selectByWrapperLambda2(){

        List<User> userList = new LambdaQueryChainWrapper<User>(mapper)
                .like(User::getName, "雨").ge(User::getAge, 20).list();
        userList.forEach(System.out::println);
    }
    /*
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND age >= ?)
==> Parameters: %雨%(String), 20(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==      Total: 2
 */

    /**
     自定义sql
     */
    @Test
    public void selectByWrapperSql(){

        LambdaQueryWrapper<User> lambdaQuery=Wrappers.<User>lambdaQuery();
        lambdaQuery.likeRight(User::getName,"王")
        .and(lqw->lqw.lt(User::getAge,40).or().isNotNull(User::getEmail));
        List<User>userList=mapper.selectAll(lambdaQuery);
        userList.forEach(System.out::println);
        userList.forEach(System.out::println);
    }
/*
==>  Preparing: select * from user WHERE (name LIKE ? AND (age < ? OR email IS NOT NULL))
==> Parameters: 王%(String), 40(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1088248166370832385, 王天风, 25, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22
<==      Total: 1
 */

    /**
     分页查询
     1.先创建配置类
     */
    @Test
    public void selectByWrapperPager(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();

        queryWrapper.ge("age",26);
        //只要记录，不要条数（）省去sql
        //构造器第三个参数，传false即可
        //Page<User> page=new Page<User>(1,2);
        Page<User> page=new Page<User>(1,2,false);
        //IPage<User> iPage=mapper.selectPage(page,queryWrapper);
        IPage<User> iPage=mapper.selectPage(page,queryWrapper);
        System.out.println("总页数"+iPage.getPages());
        System.out.println("总记录数"+iPage.getTotal());
        List<User>userList=iPage.getRecords();
        userList.forEach(System.out::println);
    }
/*
创建配置类之前
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age >= ?)
==> Parameters: 26(Integer)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16
<==      Total: 4
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5327a06e]
总页数0
总记录数0

//创建配置类之后
JsqlParserCountOptimize sql=SELECT  id,name,age,email,manager_id,create_time  FROM user

 WHERE (age >= ?)
==>  Preparing: SELECT COUNT(1) FROM user WHERE (age >= ?)
==> Parameters: 26(Integer)
<==    Columns: COUNT(1)
<==        Row: 4
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age >= ?) LIMIT ?
==> Parameters: 26(Integer), 2(Long)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==      Total: 2
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1cb19dba]
总页数2
总记录数4

//不检索总数
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE (age >= ?) LIMIT ?
==> Parameters: 26(Integer), 2(Long)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==      Total: 2
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@441fbe89]
总页数0
总记录数0
 */

    /**
     分页查询 第二种方式
     */
    @Test
    public void selectByWrapperPager2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();

        queryWrapper.ge("age",26);
        Page<User> page = new Page<User>(1,2);
        //IPage<User> iPage=mapper.selectPage(page,queryWrapper);

        /*IPage<Map<String,Object>> iPage = mapper.selectMapsPage(page, queryWrapper);
        System.out.println("总页数"+iPage.getPages());
        System.out.println("总记录数"+iPage.getTotal());
        List<Map<String, Object>> userList = iPage.getRecords();
        userList.forEach(System.out::println);*/
    }

    /**
     多表连查需要自定义sql
     */
    @Test
    public void selectByWrapperPager3(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();

        queryWrapper.ge("age",26);
        Page<User> page=new Page<User>(1,2);
        IPage<User> iPage = mapper.selectUserPage(page, queryWrapper);

        System.out.println("总页数"+iPage.getPages());
        System.out.println("总记录数"+iPage.getTotal());
        List<User>userList=iPage.getRecords();
        userList.forEach(System.out::println);
    }
/*
JsqlParserCountOptimize sql=SELECT
    *
    FROM
    user
    WHERE (age >= ?)
==>  Preparing: SELECT COUNT(1) FROM user WHERE (age >= ?)
==> Parameters: 26(Integer)
<==    Columns: COUNT(1)
<==        Row: 4
==>  Preparing: SELECT * FROM user WHERE (age >= ?) LIMIT ?
==> Parameters: 26(Integer), 2(Long)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16
<==      Total: 2
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@34a0ef00]
总页数2
总记录数4
 */


    //TODO 更新
    //
    /**
     1.updateById
     根据id更新
     不为null的更新
     updateById
     2.根据wrapper更新
     */
    @Test
    public void update1(){
        //和查询一样，可以传入一个构造器
        //User whereUser=new User（）；
        //whereUser.setName（"李艺伟"）；
        //UpdateWrapper<User> updateWrapper=new UpdateWrapper<User>（whereUser）；

        UpdateWrapper<User> updateWrapper=new UpdateWrapper<User>();
        updateWrapper.eq("name","李艺伟").eq("age",28);
        User user = new User();
        user.setEmail("lyw2019@baomidou.com");
        mapper.update(user,updateWrapper);
    }
/*
==>  Preparing: UPDATE user SET email=? WHERE (name = ? AND age = ?)
==> Parameters: lyw2019@baomidou.com(String), 李艺伟(String), 28(Integer)
<==    Updates: 1
 */
    /**
     如果只更新一两个字段
     */

    @Test
    public void update2(){
        //和查询一样，可以传入一个构造器
        //User whereUser=new User（）；
        //whereUser.setName（"李艺伟"）；
        //UpdateWrapper<User> updateWrapper=new UpdateWrapper<User>（whereUser）；

        /*UpdateWrapper<User> updateWrapper=new UpdateWrapper<User>();
        updateWrapper.eq("name","李艺伟").eq("age",28);
        User user = new User();
        user.setEmail("lyw2019@baomidou.com");
        mapper.update(user,updateWrapper);*/

        UpdateWrapper<User>updateWrapper=new UpdateWrapper<User>();
        updateWrapper.eq("name","李艺伟").eq("age",28).set("age",30);
        int rows=mapper.update(null,updateWrapper);
        System.out.println("影响记录数："+rows);
    }
/*
==>  Preparing: UPDATE user SET age=? WHERE (name = ? AND age = ?)
==> Parameters: 30(Integer), 李艺伟(String), 28(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1a3e5f23]
影响记录数：1
 */

    /**
     lambda
     */

    @Test
    public void lambdaUpdate(){
        LambdaUpdateWrapper<User> lambdaUpdate =Wrappers.<User>lambdaUpdate();
        lambdaUpdate.eq(User::getName,"李艺伟").eq(User::getAge,30).set(User::getAge,31);
        int rows=mapper.update(null,lambdaUpdate);
        System.out.println("影响记录数："+rows);
    }
/*
==>  Preparing: UPDATE user SET age=? WHERE (name = ? AND age = ?)
==> Parameters: 31(Integer), 李艺伟(String), 30(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@46866946]
影响记录数：1
 */

    /**
     链式更新
     */

    @Test
    public void lambdaUpdate2(){
        boolean update = new LambdaUpdateChainWrapper<User>(mapper)
            .eq(User::getName,"李艺伟").eq(User::getAge,31).set(User::getAge,32).update();
        System.out.println(update);
    }
/*
==>  Preparing: UPDATE user SET age=? WHERE (name = ? AND age = ?)
==> Parameters: 32(Integer), 李艺伟(String), 30(Integer)
<==    Updates: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@58d63b16]
false

==>  Preparing: UPDATE user SET age=? WHERE (name = ? AND age = ?)
==> Parameters: 32(Integer), 李艺伟(String), 31(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5d0b0cb9]
true
 */

    // TODO 删除
    /**
     根据id删除
     根据Map条件删除
     批量删除
     构造器删除
     */
    @Test
    public void delete(){
        //根据id删除
       /* int rows=mapper.deleteById(1104221411195232258L);
        System.out.println("删除条数："+rows);*/

        //根据Map条件删除
        /*Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("name","向后");
        columnMap.put("age",25);
        int rows=mapper.deleteByMap(columnMap);
        System.out.println("删除条数："+rows);*/

        //批量删除
        /*int rows=mapper.deleteBatchIds(Arrays.asList(1104220508505546754L,1104216373458722818L,
        1104215684997255169L));
        System.out.println("删除条数："+rows);*/

        //构造器删除
        LambdaQueryWrapper<User> lambdaQuery=Wrappers.<User> lambdaQuery();
        lambdaQuery.eq(User::getAge,27). or(). gt(User::getAge,41);
        int rows=mapper.delete(lambdaQuery);
        System.out.println("删除条数："+rows);
    }
/*

 */
    //TODO ActiveRecord模式
    /**
     新增
     */
    @Test
    public void insertAR(){
       /* User user=new User();
        user.setName("刘花");
        user.setAge(29);
        user.setEmail("Ih@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insert = user.insert();//这样就可以插入数据，实现动态绑定*/

        User user=new User();
        user.setName("刘花-1");
        user.setAge(28);
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insert = user.insert();//这样就可以插入数据，实现动态绑定
    }
/*
==>  Preparing: INSERT INTO user ( id, name, age, email, manager_id, create_time ) VALUES ( ?, ?, ?, ?, ?, ? )
==> Parameters: 1328175613846011906(Long), 刘花(String), 29(Integer), Ih@baomidou.com(String), 1088248166370832385(Long), 2020-11-16T11:18:37.267(LocalDateTime)
<==    Updates: 1

//为null的时候,不会增加
==>  Preparing: INSERT INTO user ( id, name, age, manager_id, create_time ) VALUES ( ?, ?, ?, ?, ? )
==> Parameters: 1328176061785092098(Long), 刘花-1(String), 28(Integer), 1088248166370832385(Long), 2020-11-16T11:20:24.080(LocalDateTime)
<==    Updates: 1
 */


    /**
     查询
     */
    @Test
    public void selectAR(){
        /*User user=new User();
        User userSelect=user.selectById(1088248166370832385L);
        System.out.println(userSelect==user);//false 查出来的不是原来那个
        System. out.println(userSelect);*/

        //也可以放在entity上
        User user=new User();
        user.setId(1088248166370832385L);
        User userSelect=user.selectById();
        System.out.println(userSelect==user);//false 查出来的不是原来那个
        System. out.println(userSelect);

    }
/*
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE id=?
==> Parameters: 1088248166370832385(Long)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1088248166370832385, 王天风, 25, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2d5f7182]
false
User(id=1088248166370832385, Name=王天风, age=25, email=wtf@baomidou.com, managerId=1087982257332887553, createTime=2019-02-05T11:12:22)
 */

    /**
     更新
     */
    @Test
    public void updateAR(){

        User user=new User();
        user.setId(1088248166370832385L);
        user.setName("王天风2");
        boolean b = user.updateById();
        System. out.println(b);

    }
/*
==>  Preparing: UPDATE user SET name=? WHERE id=?
==> Parameters: 王天风2(String), 1088248166370832385(Long)
<==    Updates: 1
 */

    /**
     删除（删除逻辑上不存在，也算成功）
     */
    @Test
    public void deleteAR(){
        User user=new User();
        user.setId(1088248166370832385L);
        boolean b = user.deleteById();
        System. out.println(b);
    }
    /**
     其他方法和曾删改查差不多
     不过selectOne炒过一条，不报错，打印警告，取第一条
     */
/*

 */

    /**
     设置了id，会先去查询，如果有，则更新，如果没有，则新增
     */
    @Test
    public void updateAR2(){
        /*User user=new User();
        user.setName("张强");
        user.setAge(24);
        user.setEmail("zq@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insertOrUpdate=user.insertOrUpdate();
        System. out.println(insertOrUpdate);*/

        User user=new User();
        user.setName("张强2");
        user.setAge(25);
        user.setId(1328217349200916481L);
        user.setEmail("zq@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insertOrUpdate=user.insertOrUpdate();
        System. out.println(insertOrUpdate);

    }
    /*
    没有id,直接新增
==>  Preparing: INSERT INTO user ( id, name, age, email, manager_id, create_time ) VALUES ( ?, ?, ?, ?, ?, ? )
==> Parameters: 1328217349200916481(Long), 张强(String), 24(Integer), zq@baomidou.com(String), 1088248166370832385(Long), 2020-11-16T14:04:27.765(LocalDateTime)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@f72203]
true

//id存在则先查询,再更新
==>  Preparing: SELECT id,name,age,email,manager_id,create_time FROM user WHERE id=?
==> Parameters: 1328217349200916481(Long)
<==    Columns: id, name, age, email, manager_id, create_time
<==        Row: 1328217349200916481, 张强, 24, zq@baomidou.com, 1088248166370832385, 2020-11-16 14:04:28
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@f72203]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7ed9499e] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@59382310 wrapping com.mysql.cj.jdbc.ConnectionImpl@6f4ade6e] will not be managed by Spring
==>  Preparing: UPDATE user SET name=?, age=?, email=?, manager_id=?, create_time=? WHERE id=?
==> Parameters: 张强2(String), 25(Integer), zq@baomidou.com(String), 1088248166370832385(Long), 2020-11-16T15:01:13.312(LocalDateTime), 1328217349200916481(Long)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7ed9499e]
true
     */

    /**
     删除（删除逻辑上不存在，也算成功）
     */
    @Test
    public void deleteAR2(){
        User user=new User();
        user.setId(1088248166370832385L);
        boolean b = user.deleteById();
        System.out.println(b);
    }
    /**
     其他方法和曾删改查差不多
     不过selectOne炒过一条，不报错，打印警告，取第一条
     */
    /*

     */

}
