package com.zyproject.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @program: zyproject
 * @description: redis配置
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-19
 **/
@Configuration
@EnableRedisHttpSession
public class RedisConfig {
}
