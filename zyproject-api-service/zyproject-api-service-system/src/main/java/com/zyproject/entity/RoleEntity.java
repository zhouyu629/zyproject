package com.zyproject.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: zyproject
 * @description: 角色实体类
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-16
 **/
@Data
public class RoleEntity implements Serializable {

    private Integer role_id;
    private String role_name;
    private int taxis;
    private int del_flag;
    private Date create_time;
    private Date last_edit_time;
    private String remark;
}
