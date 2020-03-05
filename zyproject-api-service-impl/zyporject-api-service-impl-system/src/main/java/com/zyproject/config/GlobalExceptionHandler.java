package com.zyproject.config;

import com.zyproject.common.CodeEnum;
import com.zyproject.common.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: zyproject
 * @description: 全局异常处理
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 所有不可预知错误
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseData GlobalExceptionHandler(Exception e){
        logger.error("捕获到异常："+e.getMessage());
        return ResponseData.out(CodeEnum.FAIL,null);
    }

    @ResponseBody
    @ExceptionHandler({BusinessException.class})
    public ResponseData businessException(HttpServletRequest request, HttpServletResponse response,BusinessException e){
        logger.error("捕获取business异常："+e.getCodeEnum().getCode());
        return ResponseData.out(e.getCodeEnum(),null);
    }
}
