package com.zyproject.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserEntity implements Serializable {
    private Integer user_id;
    private String login_code;
    private String login_password;
    private String user_name;
    private String mobile;
    private String pass_salt;
    private String user_face;
    private int login_count;
    private int del_flag;
    private Date create_time;
    private Date last_edit_time;
    private String remark;
}
