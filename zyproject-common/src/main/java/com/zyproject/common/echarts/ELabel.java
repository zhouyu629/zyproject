package com.zyproject.common.echarts;

import lombok.Data;

/**
 * @program: zyproject
 * @description: echarts label
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-27
 **/
@Data
public class ELabel {
    public boolean show;
    public String format;
    public String postion;
    public ELabel(boolean show,String format,String postion){
        this.show = show;
        this.format = format;
        this.postion = postion;
    }
}
