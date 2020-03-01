package com.zyproject.web.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @program: zyproject
 * @description: 注册标签到Freemarker
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-18
 **/
@Component
public class CustomeFreemakerConfigure implements ApplicationContextAware {
    @Autowired
    Configuration configuration;

    @Autowired
    private PermissionTagDirective permissionTagDirective;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @PostConstruct
    public void setSharedVariable() throws IOException, TemplateException{
        configuration.setSharedVariable("perm",permissionTagDirective);
    }
}
