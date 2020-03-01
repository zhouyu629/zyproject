package com.zyproject.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: zyproject
 * @description: 系统功能菜单树
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-10
 **/
@Data
public class TreeEntity implements Serializable {

    private int tree_id;
    private String tree_name;
    private int sup_tree_id;
    private String link_addr;
    private String tree_icon;
    private int tree_sort;
    private int enable_flag;
    private int allow_edit;
    private int allow_del;
    private int del_flag;
    private int taxis;
    private Date create_time;
    private Date last_edit_time;
    private String remark;
}
