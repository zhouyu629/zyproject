package com.zyproject.web.feignclient;

import com.zyproject.service.IApiInitService;
import com.zyproject.service.ISystemService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: zyproject
 * @description: 系统登录方法
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
@FeignClient(name = "zyproject-api-service-system",contextId = "apiinit",configuration = {})
public interface ApiInitFeign extends IApiInitService {
}
