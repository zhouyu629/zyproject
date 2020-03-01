package com.zyproject.web.service;

import com.google.gson.Gson;
import com.zyproject.common.ResponseData;
import com.zyproject.entity.UserEntity;
import com.zyproject.web.feignclient.SystemFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: zyproject
 * @description: 用户服务
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-11
 **/
@Service
public class UserService {
    @Autowired
    private SystemFeign systemFeign;

    //用户登录
    public ResponseData userLogin(@RequestParam String user_code, @RequestParam String password){
        return systemFeign.userLogin(user_code,password);
    }

    //根据登录名，获取用户信息
    public ResponseData findByLoginname(String login_name){
        return systemFeign.findByLoginname(login_name);
    }

    public ResponseData getUserList(@RequestParam(name = "page",defaultValue = "1",required = false) int page,
                                    @RequestParam(name = "pagesize",defaultValue = "10",required = false) int pagesize,
                                    @RequestParam String user_name,
                                    @RequestParam String login_code,
                                    @RequestParam Integer del_flag){
        return this.systemFeign.getUserList(page,pagesize,user_name,login_code,del_flag);
    }

    /**
     * 根据用户ID查询用户信息
     * @param user_id
     * @return
     */
    public ResponseData getUserByUserId(int user_id){
        return this.systemFeign.getUserByUserId(user_id);
    }

    /**
     * 新增或修改用户
     * @param userEntity
     * @return
     */
    public ResponseData addOrUpdateUser(UserEntity userEntity){
        if(StringUtils.isNotEmpty(userEntity.getLogin_password())){
            userEntity.setLogin_password(DigestUtils.md5DigestAsHex(userEntity.getLogin_password().getBytes()));
        }
        return systemFeign.addOrUpdateUser(new Gson().toJson(userEntity));
    }

    /**
     * 删除用户（假删除）
     * @param user_id
     * @return
     */
    public ResponseData deluser(int user_id){
        return this.systemFeign.deluser(user_id);
    }

    /**
     * 根据用户id查询用户已分配角色和未分配角色列表
     * @param user_id：用户id
     * @return
     */
    public ResponseData getuserroleinfo(int user_id){
        return this.systemFeign.getuserroleinfo(user_id);
    }
    //用户角色设置提交
    public ResponseData userroleSubmit(Integer user_id,String roleids){
        return systemFeign.userroleSubmit(user_id,roleids);
    }
}
