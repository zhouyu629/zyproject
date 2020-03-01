package com.zyproject.common.echarts;

import lombok.Data;

/**
 * @program: zyproject
 * @description: title属性的textstyle属性
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-27
 **/
@Data
public class ETitleTextStyle {
    /*
    主标题文字的颜色。
     */
    private String color = "#000";
    /*
    主标题文字字体的风格
    normal、italicoblique
     */
    private String fontStyle = "normal";
    /*
    可选：
    'normal'
    'bold'
    'bolder'
    'lighter'
    100 | 200 | 300 | 400...
     */
    private String fontWeight = "normal";
    private String fontFamily = "Microsoft YaHei";//主标题文字的字体系列。还可以是 'serif' , 'monospace', 'Arial', 'Courier New', 'Microsoft YaHei', ...
    private Integer fontSize = 18; //字号
    private Integer lineHeight; //行高。
    //太多了，其他的不写了，后面再扩展吧
}
