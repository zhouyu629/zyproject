package com.zyproject;

import com.zyproject.config.ExceptionFilter;
import com.zyproject.config.JwtAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
public class AppService {
    public static void main(String[] args){
        SpringApplication.run(AppService.class,args);
    }

    //¹ýÂËÆ÷
    @Bean
    public FilterRegistrationBean erroFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        ExceptionFilter exceptionFilter = new ExceptionFilter();
        filterRegistrationBean.setFilter(exceptionFilter);
        filterRegistrationBean.setName("exceptionFilter");
        filterRegistrationBean.setOrder(-1);
        return  filterRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean jwtFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        filterRegistrationBean.setFilter(jwtAuthenticationFilter);
        return filterRegistrationBean;
    }
}
