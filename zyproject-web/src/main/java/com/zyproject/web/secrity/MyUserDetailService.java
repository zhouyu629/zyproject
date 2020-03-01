package com.zyproject.web.secrity;

import com.google.gson.Gson;
import com.zyproject.common.CodeEnum;
import com.zyproject.common.ResponseData;
import com.zyproject.entity.RoleTreefuncEntity;
import com.zyproject.entity.UserEntity;
import com.zyproject.web.service.TreeService;
import com.zyproject.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @program: zyproject
 * @description: 实现SpringSecrity UserDetailService接口
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-12
 **/
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private TreeService treeService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ResponseData<UserEntity> userResult = userService.findByLoginname(s);
        UserEntity user = new UserEntity();
        if(userResult.getCode() == CodeEnum.SUCCESS.getCode()){
            user = userResult.getData(UserEntity.class);
            ResponseData rightResult = treeService.getRoleTreefuncByUserid(user.getUser_id());
            Gson gson = new Gson();
            List<RoleTreefuncEntity> roleTreefuncEntities = Arrays.asList(gson.fromJson(gson.toJson(rightResult.getData()),RoleTreefuncEntity[].class));
            return new MyUserDetails(user,roleTreefuncEntities);
        }else{
            throw new UsernameNotFoundException(s);
        }
    }
}
