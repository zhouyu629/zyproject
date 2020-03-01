package com.zyproject.common.echarts;

import lombok.Data;

/**
 * @program: zyproject
 * @description: echarts legend属性
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-27
 **/
@Data
public class ELegend {
    /*
    图例的类型。可选值：
    'plain'：普通图例。缺省就是普通图例。
    'scroll'：可滚动翻页的图例。当图例数量较多时可以使用。
     */
    private String type = "plain";
    private boolean show = true; //是否显示图例
    public ELegend(String type,boolean show){
        this.show = show;
        this.type = type;
    }
    //太多了，其他的回头再扩展吧
}
