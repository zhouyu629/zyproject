package com.zyproject.web.manage;

import com.zyproject.common.CodeEnum;
import com.zyproject.common.ResponseData;
import com.zyproject.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: zyproject
 * @description: 后台管理登录控制器
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-10
 **/
@Controller
@RequestMapping("/manage")
public class LoginController {
    @Autowired
    private UserService userService;

    //登录界面
    @RequestMapping("/login")
    public String login(){
        return "manage/login";
    }

    //登录提交
    @RequestMapping("/login-submit")
    @ResponseBody
    public ResponseData loginSubmit(String username,String password){
        System.out.print("user_code:"+username);
        System.out.print("password:"+password);
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //调用service登录方法进行验证
        ResponseData data = userService.userLogin(username,password);
        if(data.getCode() == CodeEnum.SUCCESS.getCode()){
            //登录成功
        }else{
            //通用出错界面
        }
        return data;
    }
}
