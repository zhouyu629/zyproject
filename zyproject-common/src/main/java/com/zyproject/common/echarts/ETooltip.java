package com.zyproject.common.echarts;

import lombok.Data;

/**
 * @program: zyproject
 * @description: 提示框组件。
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-27
 **/
@Data
public class ETooltip {
    private boolean show = true;
    /**
     * 触发类型。
     * 可选：
     * 'item'
     * 数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
     * 'axis'
     * 坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用。
     * 在 ECharts 2.x 中只支持类目轴上使用 axis trigger，在 ECharts 3 中支持在直角坐标系和极坐标系上的所有类型的轴。并且可以通过 axisPointer.axis 指定坐标轴。
     *
     * 'none'   * 什么都不触发。
     */
    private String trigger = "axis";
}
