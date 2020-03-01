package com.zyproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: zyproject
 * @description: WEB启动入口
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-10
 **/
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AppWeb {
    public static void main(String[] args){
        SpringApplication.run(AppWeb.class,args);
    }
}
