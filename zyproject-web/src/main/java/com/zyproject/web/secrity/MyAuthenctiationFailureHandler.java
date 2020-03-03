package com.zyproject.web.secrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: zyproject
 * @description: 自定义登录失败处理
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-12
 **/
@Component("myAuthenctiationFailureHandler")
public class MyAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.error(exception.getLocalizedMessage());
        super.onAuthenticationFailure(request, response, exception);
    }
}
