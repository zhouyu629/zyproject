package com.zyproject.web.feignclient;

import com.zyproject.service.ISystemService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: zyproject
 * @description: RPC调用系统管理相关接口服务
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-11
 **/
@FeignClient("zyproject-api-service-system")
public interface SystemFeign extends ISystemService {

}
