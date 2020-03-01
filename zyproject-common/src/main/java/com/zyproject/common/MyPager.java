package com.zyproject.common;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: zyproject
 * @description: 分页通用实体类
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-16
 **/
@Data
public class MyPager<T> {
    private int page; //当前页码，从1开始
    private int pagesize; //分页大小
    private int totalpages; //总页数
    private int totalrecords; //总记录数
    private List<T> list;
    //构造函数中计算总页数
    public MyPager(int page,int pagesize,int totalrecords,List<T> list){
        this.page = page;
        this.pagesize = pagesize;
        this.totalrecords = totalrecords;
        this.list = list;
        //计算总页数
        this.totalpages = totalrecords/pagesize;
        if(totalrecords%pagesize!=0){
            this.totalpages = this.totalpages + 1;
        };
    }
}
