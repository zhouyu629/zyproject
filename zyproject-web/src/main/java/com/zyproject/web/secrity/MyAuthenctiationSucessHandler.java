package com.zyproject.web.secrity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: zyproject
 * @description: SpringSecurity登录成功句柄
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-13
 **/
@Component("MyAuthenctiationSucessHandler")
public class MyAuthenctiationSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
