package com.zyproject.service;

import com.zyproject.common.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: zyproject
 * @description: 系统初始化，用户token分配
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
public interface IApiInitService {
    @GetMapping("/init/login")
    public ResponseData login(@RequestParam String user_name);
}
