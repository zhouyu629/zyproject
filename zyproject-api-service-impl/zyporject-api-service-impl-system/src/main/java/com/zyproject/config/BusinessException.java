package com.zyproject.config;

import com.zyproject.common.CodeEnum;
import lombok.Data;

/**
 * @program: zyproject
 * @description: 自定义业务异常类
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-03-04
 **/
@Data
public class BusinessException extends RuntimeException {
    private CodeEnum codeEnum;
    public BusinessException(CodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.codeEnum = codeEnum;
    }
}
