package com.zyproject.common;

import com.google.gson.Gson;

import java.util.List;

/**
 * @program: zyproject
 * @description: 响应数据结构封装
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-11
 **/
public class ResponseData<T> extends BaseReponse {
    private T data;
    private ResponseData(){}
    private ResponseData(CodeEnum codeEnum, T data){
        super(codeEnum);
        this.data = data;
    }
    public static <T> ResponseData<T> out(CodeEnum codeEnum,T data){
        return new ResponseData<T>(codeEnum,data);
    }

    public T getData(){
        return data;
    }

    //RPC后，会转成HasMap，无法直接转换成T类型，需要扩展
    public T getData(Class<T> clazz){
        Gson gson = new Gson();
        T data2 = gson.fromJson(gson.toJson(this.data),clazz);
        return data2;
    }

    public List<T> getData2(Class<List<T>> clazz){
        Gson gson = new Gson();
        List<T> data2 = gson.fromJson(gson.toJson(this.data),clazz);
        return data2;
    }

    public void setData(T data){
        this.data = data;
    }
}
