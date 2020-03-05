package com.zyproject.web.manage;

import com.zyproject.common.CodeEnum;
import com.zyproject.common.ResponseData;
import com.zyproject.web.feignclient.ApiInitFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

/**
 * @program: zyproject
 * @description: 登录拿jwt token认证
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
@Service
public class ApiInitService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApiInitFeign apiInitFeign;
    public String getToken(){
        logger.info("请求token");
        ResponseData responseData = this.apiInitFeign.login("admin");
        if(responseData.getCode() == CodeEnum.SUCCESS.getCode()){
            return responseData.getData().toString();
        }else {
            return "";
        }
    }
}
