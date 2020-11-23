package com.poi.mybatisplus.demo.comfig;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: mybstis plus配置类
 * @Author: songkai
 * @Date: 2020/11/11
 * @Version: 1.0
 */
@Configuration
public class MybatisPlusConfig{

    @Bean
    public PaginationInterceptor paginationlnterceptor(){
        return new PaginationInterceptor();
    }
}
