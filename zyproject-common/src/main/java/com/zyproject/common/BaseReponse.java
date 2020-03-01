package com.zyproject.common;

/**
 * @program: zyproject
 * @description: 基本响应类封装
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-11
 **/
public class BaseReponse {
    private int code; //响应码
    private String msg; //响应消息
    protected  BaseReponse(){}
    protected  BaseReponse(CodeEnum codeEnum){
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }
    public static BaseReponse out(CodeEnum codeEnum){
        return new BaseReponse(codeEnum);
    }
    public int getCode(){return code;}
    public void setCode(int code){this.code = code;}
    public String getMsg(){return msg;}
    public void setMsg(String msg){this.msg = msg;}
}
