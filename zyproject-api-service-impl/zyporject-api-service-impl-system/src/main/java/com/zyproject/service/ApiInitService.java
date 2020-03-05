package com.zyproject.service;

import com.zyproject.common.CodeEnum;
import com.zyproject.common.ResponseData;
import com.zyproject.config.BusinessException;
import com.zyproject.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: zyproject
 * @description: API初始化相关
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
@RestController
@RequestMapping("/init")
public class ApiInitService implements IApiInitService {

    //控制器统一出错信息
    @RequestMapping("/api-error")
    public ResponseData apierror(HttpServletRequest request){
        Exception e = (Exception)request.getAttribute("filter.error");
        try{
            //应该对Exception做更细致的划分
            BusinessException be = (BusinessException)e;
            return ResponseData.out(be.getCodeEnum(),null);
        }catch (Exception ex) {
            return ResponseData.out(CodeEnum.FAIL,e.getMessage());
        }
    }

    //用户登录
    @GetMapping("/login")
    @Override
    public ResponseData login(String user_name) {
        if("admin".equals(user_name)){
            String jwt = JwtUtil.generatorToken(user_name);
            return ResponseData.out(CodeEnum.SUCCESS,jwt);
        }else {
            return ResponseData.out(CodeEnum.UNAUTHORIZEDUSERNAMEORPASSWORDINVALID, null);
        }
    }
}
