package com.zyproject.web.manage;

import com.google.gson.Gson;
import com.zyproject.common.CodeEnum;
import com.zyproject.common.MyPager;
import com.zyproject.common.ResponseData;
import com.zyproject.entity.RoleEntity;
import com.zyproject.entity.UserEntity;
import com.zyproject.web.service.RoleService;
import com.zyproject.web.service.TreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zyproject
 * @description: 角色管理控制器
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-16
 **/
@Controller
@RequestMapping("/manage/role")
public class RoleController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TreeService treeService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    //角色管理首页
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int pagesize,
                        @RequestParam int tree_id, Map<Object,Object> map){
        ResponseData roles = roleService.getRoleByPage(page,pagesize);
        map.put("pager",roles.getData());
        map.put("user",this.getCurrentUser());
        return "/manage/role/index";
    }
    //角色权限设置
    @RequestMapping("/rolerightset")
    public String rolerightset(@RequestParam int role_id,Map<Object,Object> map){
        ResponseData<RoleEntity> result = this.roleService.getRoleByRoleid(role_id);
        RoleEntity roleEntity = result.getData(RoleEntity.class);
        map.put("role",roleEntity);
        //获取菜单树及按钮
        ResponseData<Map<String,Object>> tree = this.treeService.getAllTreeWithRoleRight(role_id);
        Map<String,Object> treeMap = tree.getData();
        map.put("func",treeMap.get("func"));
        map.put("tree",treeMap.get("tree"));
        return "/manage/role/rolerightset";
    }

    //根据role_id获得role
    @RequestMapping("/getrole")
    @ResponseBody
    public ResponseData getrole(Integer id){
        ResponseData result = this.roleService.getRoleByRoleid(id);
        logger.info(result.getMsg());
        return result;
    }

    //角色新增或编辑提交
    @RequestMapping("/roleedit-submit")
    @ResponseBody
    public ResponseData roleeditSubmit(RoleEntity role){
        ResponseData responseData = this.roleService.addOrUpdateRole(role);
        return responseData;
    }

    //删除角色
    @RequestMapping("/del")
    @ResponseBody
    public ResponseData del(Integer id){
        return this.roleService.delrole(id);
    }

    //角色设置表单提交
    @RequestMapping("/rolerightset-submit")
    public String rolerightsetSubmit(@RequestParam int role_id,@RequestParam String[] chkids){
        logger.info("checkbox:"+chkids.length);
        List<String> treeids = new ArrayList<>();
        List<Map<String,String>> btnids = new ArrayList<>();
        //将菜单和按钮拆开，先组装成java类型，再用gson转，保证服务侧能转换
        for (String id: chkids) {
            String[] ids = id.split("_");
            if(id.indexOf("tree_")>=0){
                treeids.add(ids[1]);
            }else if(id.indexOf("btn_")>=0){
                Map<String,String> map = new HashMap<>();
                map.put("tree_id",ids[1]);
                map.put("btn_id",ids[2]);
                btnids.add(map);
            }
        }
        //为兼容其他语言，将List转成json字符串进行Feign
        Gson gson = new Gson();
        String str_tree = gson.toJson(treeids);
        String str_btn = gson.toJson(btnids);
        this.roleService.setRoleRight(role_id,str_tree,str_btn);
        return "/manage/role/index?tree_id="+this.getTreeid();
    }
}
