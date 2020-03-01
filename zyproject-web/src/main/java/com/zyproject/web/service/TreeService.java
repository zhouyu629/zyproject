package com.zyproject.web.service;

import com.zyproject.common.CodeEnum;
import com.zyproject.common.ResponseData;
import com.zyproject.entity.TreeEntity;
import com.zyproject.web.feignclient.SystemFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: zyproject
 * @description: 菜单树
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-15
 **/
@Service
public class TreeService {

    @Autowired
    private SystemFeign systemFeign;

    /**
     * 获取用户权限菜单
     * @param userid 用户id
     * @return
     */
    public ResponseData getTreeWithUserRight(@RequestParam(name = "userid") int userid){
       return systemFeign.getTreeWithUserRight(userid);
    }

    /**
     * 根据菜单id查找菜单实体
     * @param tree_id 菜单编号
     * @return
     */
    public ResponseData getTreeByTreeId(@RequestParam(name="tree_id") int tree_id){
        return systemFeign.getTreeByTreeId(tree_id);
    }

    //查找所有的树，含当前用户是否有权限

    /**
     * 根据用户查询所有权限按钮
     * @param user_id：用户ID
     * @return
     */
    public ResponseData getRoleTreefuncByUserid(@RequestParam int user_id){
        return systemFeign.getRoleTreefuncByUserid(user_id);
    }

    /**
     * 获取所有树及按钮，含当前用户是否有权限
     * @param role_id
     * @return
     */
    public ResponseData getAllTreeWithRoleRight(@RequestParam int role_id){
        return this.systemFeign.getAllTreeWithRoleRight(role_id);
    }
}
