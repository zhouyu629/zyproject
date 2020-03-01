package com.zyproject.web.service;

import com.google.gson.Gson;
import com.zyproject.common.ResponseData;
import com.zyproject.entity.RoleEntity;
import com.zyproject.web.feignclient.SystemFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @program: zyproject
 * @description: 角色管理
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-16
 **/
@Service
public class RoleService {
    @Autowired
    private SystemFeign systemFeign;

    /**
     * 分页获取角色列表
     * @param page
     * @param pagesize
     * @return
     */
    public ResponseData getRoleByPage(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int pagesize)
    {
        return systemFeign.getRoleByPage(page,pagesize);
    }

    /**
     * 根据role_id获取角色信息
     * @param role_id：角色 ID
     * @return
     */
    public ResponseData getRoleByRoleid(@RequestParam  int role_id){
        return systemFeign.getRoleByRoleid(role_id);
    }

    /**
     * 设置角色权限
     * @param role_id
     * @param tree_ids：考虑到对其他语言的兼容性，不建议传java类型
     * @param btn_ids：考虑到对其他语言的兼容性，不建议传java类型
     * @return
     */
    public ResponseData setRoleRight(@RequestParam int role_id,
                                     @RequestParam(name = "tree_ids",required = true) String  tree_ids,
                                     @RequestParam(name = "btn_ids",required = true) String btn_ids){
        return systemFeign.setRoleRight(role_id,tree_ids,btn_ids);
    }

    /**
     * 新增或编辑角色
     * @param roleEntity：角色信息，Feign前转换为json字符串
     * @return
     */
    public ResponseData addOrUpdateRole(RoleEntity roleEntity){
        String role = new Gson().toJson(roleEntity);
        return this.systemFeign.addOrUpdateRole(role);
    }

    /**
     * 删除角色（真实删除）
     * @param role_id
     * @return
     */
    public ResponseData delrole(Integer role_id){
        return this.systemFeign.delrole(role_id);
    }
}
