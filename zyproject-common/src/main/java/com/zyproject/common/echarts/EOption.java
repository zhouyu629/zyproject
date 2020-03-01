package com.zyproject.common.echarts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: zyproject
 * @description: Echarts实体类
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-27
 **/
@Data
public class EOption {
    private ETitle title;
    private ETooltip tooltip;
    private ELegend legend;
    @JsonProperty("xAxis")
    private EXaixs xAxis;
    @JsonProperty("yAxis")
    private EYaxis yAxis;
    private List<ESeries> series;
}
