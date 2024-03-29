package com.zyproject.common.echarts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

/**
 * @program: zyproject
 * @description: y轴
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-27
 **/
@Data
public class EYaxis {
    /**
     * 坐标轴类型。
     * 可选：
     * 'value' 数值轴，适用于连续数据。
     * 'category' 类目轴，适用于离散的类目数据，为该类型时必须通过 data 设置类目数据。
     * 'time' 时间轴，适用于连续的时序数据，与数值轴相比时间轴带有时间的格式化，在刻度计算上也有所不同，例如会根据跨度的范围来决定使用月，星期，日还是小时范围的刻度。
     * 'log' 对数轴。适用于对数数据。
     */
    private String type = "value";
    private String name = "";
    @JsonProperty("nameLocation")
    private String nameLocation;
    @JsonProperty("nameGap")
    private Integer nameGap;
    private Object[] data;
    public EYaxis(Object[] data,String type){
        this.data = data;
        this.type = type;
    }
}
