package com.zyproject.common.echarts;

import lombok.Data;

import java.util.ArrayList;

/**
 * @program: zyproject
 * @description: series
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-27
 **/
@Data
public class ESeries {
    private String name; //系列名称，用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列。
    private String type; //line,bar,pie等
    private Object[] data; //数据源
    private boolean smooth = false;
    public ESeries(String name,String type,Object[] data,boolean smooth){
        this.name = name = name;
        this.data = data;
        this.type= type;
        this.smooth = smooth;
    }
}
