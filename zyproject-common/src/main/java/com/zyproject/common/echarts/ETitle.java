package com.zyproject.common.echarts;

import lombok.Data;

/**
 * @program: zyproject
 * @description: echarts的title属性
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-27
 **/
@Data
public class ETitle {
    //太多了，写几个重要的吧
    private boolean show = true;//是否显示标题组件。
    private String text = ""; //主标题文本，支持使用 \n 换行。
    private String link = ""; //主标题文本超链接。
    private String target = ""; //指定窗口打开主标题超链接。'self' 当前窗口打开，'blank' 新窗口打开
    private ETitleTextStyle textStyle;//主标题文字的颜色。
    public ETitle(){
    }
}
