package com.zyproject.web.freemarker;

import com.zyproject.entity.RoleTreefuncEntity;
import com.zyproject.entity.UserEntity;
import com.zyproject.web.secrity.MyUserDetails;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: zyproject
 * @description: freemarker自定义标签，实现按钮级权限控制
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-18
 **/
@Component
public class PermissionTagDirective implements TemplateDirectiveModel {
    private static String permissionType = "ptype"; //A新增，D删除等，与数据库对应
    private static String Tree = "tree_id"; //菜单id
    @Override
    public void execute(Environment environment, Map map,
                        TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        String ptype = map.get(permissionType).toString();
        String tree_id = map.get(Tree).toString();
        //当前登录用户
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //判断权限，当前tree下，是否有ptype权限
        if(this.checkRight(myUserDetails.getTreefuncEntities(),ptype,tree_id)) {
            templateDirectiveBody.render(environment.getOut());
        }
    }

    private boolean checkRight(List<RoleTreefuncEntity> roleTreefuncEntities,String ptype,String tree_id){
        for (RoleTreefuncEntity roleTreefuncEntity :roleTreefuncEntities) {
            if(roleTreefuncEntity.getTree_id() == Integer.valueOf(tree_id) && roleTreefuncEntity.getTree_func_name().equals(ptype)){
                return true;
            }
        }
        return false;
    }
}
