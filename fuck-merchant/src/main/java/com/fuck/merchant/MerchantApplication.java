package com.fuck.merchant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan("com.fuck.core.mapper")
@ComponentScan(basePackages = {"com.fuck.core","com.fuck.merchant"})
public class MerchantApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerchantApplication.class, args);
    }

}