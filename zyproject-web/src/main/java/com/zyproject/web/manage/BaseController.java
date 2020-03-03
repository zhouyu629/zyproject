package com.zyproject.web.manage;

import com.zyproject.common.CodeEnum;
import com.zyproject.common.ResponseData;
import com.zyproject.entity.TreeEntity;
import com.zyproject.entity.UserEntity;
import com.zyproject.web.secrity.MyUserDetails;
import com.zyproject.web.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: zyproject
 * @description: 公共父级控制器
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-18
 **/
@Controller
public class BaseController {
    @Autowired
    private TreeService treeService;
    //当前登录用户
    protected UserEntity getCurrentUser(){
        return ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity();
    }

    public BaseController(){
        /*String tree_id = this.getRequest().getParameter("tree_id");
        if(null!=tree_id){
            this.getRequest().getSession().setAttribute("tree_id",tree_id);
        }*/
    }

    @ModelAttribute
    public void inti(Map<String,Object> map){
        String tree_id = this.getRequest().getParameter("tree_id");
        if(null!=tree_id){
            this.getRequest().getSession().setAttribute("tree_id",tree_id);
            map.put("tree",this.getTree(Integer.valueOf(tree_id)));
            map.put("tree_id",tree_id);
        }else{
            //从session里拿出来，送到前台
            if(null!=this.getRequest().getSession().getAttribute("tree_id")){
                tree_id = this.getRequest().getSession().getAttribute("tree_id").toString();
                map.put("tree",this.getTree(Integer.valueOf(tree_id)));
                map.put("tree_id",tree_id);
            }
        }
        //把用户信息也塞到前台
        map.put("user",this.getCurrentUser());
    }

    protected int getTreeid(){
        String tree_id = this.getRequest().getSession().getAttribute("tree_id").toString();
        if(null!=tree_id){
            return Integer.valueOf(tree_id);
        }else{
            return 0; //0代表无菜单
        }
    }

    protected TreeEntity getTree(int tree_id){
        String tree_id_p = this.getRequest().getParameter("tree_id");
        //只有没有再传tree_id过来，且session里有tree，才从session里拿
        if(null!=this.getRequest().getSession().getAttribute("tree") && null == this.getRequest().getParameter("tree_id")){
            //从session里直接拿tree
            return (TreeEntity)this.getRequest().getSession().getAttribute("tree");
        }else{
            //从数据库拿
            ResponseData<TreeEntity> result = this.treeService.getTreeByTreeId(tree_id);
            if(result.getCode() == CodeEnum.SUCCESS.getCode()){
                TreeEntity tree = result.getData(TreeEntity.class);
                //放入session
                this.getRequest().getSession().setAttribute("tree",tree);
                return tree;
            }else{
                return null;
            }
        }
    }

    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
