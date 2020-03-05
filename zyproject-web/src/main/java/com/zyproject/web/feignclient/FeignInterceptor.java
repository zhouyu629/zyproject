package com.zyproject.web.feignclient;

import com.zyproject.web.manage.ApiInitService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @program: zyproject
 * @description: Feign拦截器，用于在所有请求上加上header，适用于jwt token认证
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
public class FeignInterceptor implements RequestInterceptor {
    private  final String key = "Authorization";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApiInitService apiInitService;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.info("当前路径："+ request.getServletPath());
        if(request.getServletPath().indexOf("init") < 0) {
            //把key放到redies里保存起来
            if (!requestTemplate.headers().containsKey(key)) {
                //拿key
                String token = this.apiInitService.getToken();
                requestTemplate.header(key, token);
            }
        }
    }
}
