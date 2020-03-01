package com.zyproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @program: zyproject
 * @description: 分布式配置中心启动入口
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-07
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class AppConfigServer {
    public  static  void main(String[] args){
        SpringApplication.run(AppConfigServer.class,args);
    }
}
