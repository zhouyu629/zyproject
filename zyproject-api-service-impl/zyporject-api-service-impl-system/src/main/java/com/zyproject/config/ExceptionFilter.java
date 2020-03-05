package com.zyproject.config;

import javax.servlet.*;
import java.io.IOException;

/**
 * @program: zyproject
 * @description: 过滤器异常拦截
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
public class ExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try{
            filterChain.doFilter(servletRequest,servletResponse);
        }catch (Exception e){
            //异常捕获，发送到error
            servletRequest.setAttribute("filter.error",e);
            servletRequest.getRequestDispatcher("/init/api-error").forward(servletRequest,servletResponse);
        }
    }
}
