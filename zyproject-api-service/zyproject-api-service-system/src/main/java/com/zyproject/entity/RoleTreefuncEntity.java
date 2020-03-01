package com.zyproject.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: zyproject
 * @description: 角色菜单按钮权限实体类
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-18
 **/
@Data
public class RoleTreefuncEntity  implements Serializable {
    private int role_id;
    private int tree_id;
    private String tree_func_name; //权限：A新增，E编辑，D删除等
    private String tree_funcname; //对应func_name的汉字描述
}
