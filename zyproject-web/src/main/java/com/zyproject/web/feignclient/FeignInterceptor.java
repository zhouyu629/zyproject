package com.zyproject.web.feignclient;

import com.zyproject.web.config.RedisUtils;
import com.zyproject.web.manage.ApiInitService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * @program: zyproject
 * @description: Feign拦截器，用于在所有请求上加上header，适用于jwt token认证
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
public class FeignInterceptor implements RequestInterceptor {
    private  final String key = "Authorization";
    private final long exptime = 3600L; //超时时间
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApiInitService apiInitService;
    @Resource
    private RedisUtils redisUtils;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.info("当前路径："+ request.getServletPath());
        if(request.getServletPath().indexOf("init") < 0) {
            //把key放到redies里保存起来
            String authorization = (String) redisUtils.get(key);
            if (StringUtils.isEmpty(authorization)) {
                logger.info("redis中不存在，需要调用服务拿token");
                //拿key
                authorization = this.apiInitService.getToken();
                //保存到Redis中
                redisUtils.set(key,authorization,exptime, TimeUnit.HOURS);
                requestTemplate.header(key, authorization);
            }else{
                logger.info("redis中存在，直接取redis中的token");
                requestTemplate.header(key,authorization);
            }
        }
    }
}
