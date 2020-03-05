package com.zyproject.config;

import com.zyproject.common.CodeEnum;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.PathMatcher;

/**
 * @program: zyproject
 * @description: 接口认证过滤器
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(isProtectedUrl(httpServletRequest) && !isExceedUrl(httpServletRequest)){
                String token = httpServletRequest.getHeader("Authorization");
                JwtUtil.validateToken(token);
            }
        }catch (Exception e){
            //httpServletResponse.sendError(CodeEnum.UNAUTHORIZED.getCode(),e.getMessage());
            throw new BusinessException(CodeEnum.UNAUTHORIZED);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private boolean isProtectedUrl(HttpServletRequest request){
        return pathMatcher.match("/api/**",request.getServletPath());
    }
    private boolean isExceedUrl(HttpServletRequest request){
        return pathMatcher.match("/init/**",request.getServletPath());
    }
}
