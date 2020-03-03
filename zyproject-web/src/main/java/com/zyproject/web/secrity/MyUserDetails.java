package com.zyproject.web.secrity;

import com.zyproject.entity.RoleTreefuncEntity;
import com.zyproject.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @program: zyproject
 * @description: 实现SpringSercurity UserDetails
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-12
 **/
public class MyUserDetails implements UserDetails {

    private UserEntity userEntity; //用户实体
    //将用户所有的角色菜单权限放到内存中，供校验使用
    private List<RoleTreefuncEntity> roleTreefuncEntities;

    public MyUserDetails(UserEntity userEntity,List<RoleTreefuncEntity> roleTreefuncEntities){
        this.userEntity = userEntity;
        this.roleTreefuncEntities = roleTreefuncEntities;
    }

    //获取用户真实姓名
    public String getRealName(){
        return userEntity.getUser_name();
    }

    //获取用户实体
    public UserEntity getUserEntity(){
        return this.userEntity;
    }

    //获取权限菜单
    public List<RoleTreefuncEntity> getTreefuncEntities(){return this.roleTreefuncEntities;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userEntity.getLogin_password();
    }

    @Override
    public String getUsername() {
        return userEntity.getLogin_code();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
