package com.zyproject.web.secrity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: zyproject
 * @description: SpringSercrity配置
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-12
 **/
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private MyAuthenctiationSucessHandler myAuthenctiationSucessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(myUserDetailService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return MD5Util.encode((String)charSequence);
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(MD5Util.encode((String)charSequence));
            }
        });
    }


    @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.headers().frameOptions().disable();
        httpSecurity
                .authorizeRequests()
                .antMatchers("/manage/error","/manage/404","/manage/login/**","/manage/login-submit","/manage/images/**","/manage/js/**","/manage/css/**","/manage/fonts/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //指定登录路径
                .loginPage("/manage/login")
                .loginProcessingUrl("/manage/login-submit") //表单请求的路径，貌似无用，已经在config里做密码校验了
                .failureHandler(myAuthenctiationFailureHandler)
                .successHandler(myAuthenctiationSucessHandler)
                .failureUrl("/manage/error?key=1002")
                .defaultSuccessUrl("/manage/index")
                .usernameParameter("username")
                .passwordParameter("password")
                //必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                .permitAll()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/manage/error?key=timeout");
        //默认都会产生一个hiden标签 里面有安全相关的验证 防止请求伪造 这边我们暂时不需要 可禁用掉
        httpSecurity.csrf().disable();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception{
        super.configure(webSecurity);
    }
}
