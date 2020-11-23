package com.poi.mybatisplus.demo.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description: 用户
 * @Author: songkai
 * @Date: 2020/11/9
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)//没有就调用父类的hascode
public class User extends Model<User> {

    @TableId
    private Long id;
    //@TableField(condition = SqlCondition)
    private String Name;
    //@TableField(condition="%s&lt;#{%s}")
    //@TableField(condition="%s IN (%s)")
    private Integer age;
    private String email;
    //@TableField("manager_id")
    private Long managerId ;
    private LocalDateTime createTime;
}
