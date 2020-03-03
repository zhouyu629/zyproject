package com.zyproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zyproject.common.CodeEnum;
import com.zyproject.common.MyPager;
import com.zyproject.common.ResponseData;
import com.zyproject.dao.ArticleDao;
import com.zyproject.dao.RoleDao;
import com.zyproject.dao.TreeDao;
import com.zyproject.dao.UserDao;
import com.zyproject.entity.*;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: zyproject
 * @description: 系统管理实现（客户端Feign同一服务只能定义一个，这里也把所有系统管理相关的服务放到一个 Service中）
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-15
 **/
@RestController
@Api(value = "SystemService",description ="系统管理接口相关")
public class SystemService implements ISystemService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TreeDao treeDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ArticleDao articleDao;

    @GetMapping("/userLogin")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_code",value = "登录名",dataType = "String",required = true),
            @ApiImplicitParam(name = "password",value = "密码(32位小md5)",dataType = "String",required = true)
    })
    @Override
    public ResponseData userLogin(String user_code, String password) {
        UserEntity userEntity = userDao.userLogin(user_code,password);
        return ResponseData.out(userEntity!=null? CodeEnum.SUCCESS:CodeEnum.LOGINFAIL,userEntity);
    }

    @GetMapping("/findByLoginname")
    @ApiOperation("根据用户名查找用户信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "login_name",value = "登录名")
    )
    @Override
    public ResponseData findByLoginname(String login_name) {
        UserEntity userEntity = userDao.findByUsername(login_name);
        return ResponseData.out(userEntity!=null?CodeEnum.SUCCESS:CodeEnum.USERNOTEXIST,userEntity);
    }

    @GetMapping("/getUserList")
    @ApiOperation("查询用户列表，可以选择user_name,login_code,del_flag等过滤条件")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page",value = "当前页码，从1开始",required = false,dataType = "int"),
        @ApiImplicitParam(name = "pagesize",value = "分页大小，默认10",required = false,dataType = "int"),
        @ApiImplicitParam(name = "user_name",value = "搜索条件：用户姓名",required = false,dataType = "String"),
        @ApiImplicitParam(name = "login_code",value = "搜索条件：登录名",required = false,dataType = "String"),
        @ApiImplicitParam(name = "user_name",value = "搜索条件：删除标示（0未删除，1已删除）",required = false,dataType = "Integer"),
    })
    @Override
    public ResponseData getUserList(int page, int pagesize, String user_name, String login_code, Integer del_flag) {
        MyPager myPager = this.userDao.getUserList(page,pagesize,user_name,login_code,del_flag);
        return ResponseData.out(CodeEnum.SUCCESS,myPager);
    }

    @GetMapping("/getUserByUserId")
    @ApiOperation("根据用户id查找用户信息")
    @ApiImplicitParam(name = "user_id",value = "用户ID",dataType = "int",required = true)
    @Override
    public ResponseData getUserByUserId(int user_id) {
        UserEntity userEntity = this.userDao.getUserByUserId(user_id);
        return ResponseData.out(CodeEnum.SUCCESS,userEntity);
    }

    @GetMapping("/addOrUpdateUser")
    @ApiOperation("新增或修改系统用户")
    @Override
    public ResponseData addOrUpdateUser(String userStr) {
        //类型转换
        try{
            UserEntity userEntity = new Gson().fromJson(userStr,UserEntity.class);
            //必填字段判断
            if(StringUtils.isEmpty(userEntity.getLogin_code())){
                return ResponseData.out(CodeEnum.USERLOGINCODEISNULL,null);
            }else if(StringUtils.isEmpty(userEntity.getUser_name())){
                return ResponseData.out(CodeEnum.USERNAMEISNULL,null);
            }else if(StringUtils.isEmpty(userEntity.getLogin_password())){
                return ResponseData.out(CodeEnum.USERPASSWORDISNULL,null);
            }else {
                //如果是新增，判断login_code不重复
                if (null == userEntity.getUser_id()) {
                    UserEntity user = this.userDao.findByUsername(userEntity.getLogin_code());
                    if (user != null) {
                        return ResponseData.out(CodeEnum.LOGINCODEREPEAT, userEntity.getLogin_code());
                    }
                }
                return ResponseData.out(this.userDao.addOrUpdateUser(userEntity) ? CodeEnum.SUCCESS : CodeEnum.FAIL, null);
            }
        }catch (Exception ex){
            return ResponseData.out(CodeEnum.USERTYPERROR,ex.getMessage());
        }
    }

    @GetMapping("/deluser")
    @ApiOperation("删除用户（假删除）")
    @ApiImplicitParam(name = "user_id",value = "用户id",dataType = "int",required = true)
    @Override
    public ResponseData deluser(int user_id) {
        return ResponseData.out(this.userDao.deluser(user_id)?CodeEnum.SUCCESS:CodeEnum.FAIL,null);
    }

    @GetMapping("/getuserroleinfo")
    @ApiOperation("根据用户id查询用户已分配角色和未分配角色列表")
    @ApiImplicitParam(name = "user_id",value = "用户id",dataType = "int",required = true)
    @Override
    public ResponseData getuserroleinfo(int user_id) {
        Map<String,Object> map = this.userDao.getuserroleinfo(user_id);
        return ResponseData.out(CodeEnum.SUCCESS,map);
    }

    @GetMapping("/userroleSubmit")
    @ApiOperation("用户角色设置提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",value = "用户id",dataType = "int",required = true),
            @ApiImplicitParam(name = "roleids",value = "角色清单，以逗号分隔role_id。如果为空则清除该用户下所有角色",dataType = "int",required = false)
    })
    @Override
    public ResponseData userroleSubmit(Integer user_id, String roleids) {
        this.userDao.userroleSubmit(user_id,roleids);
        return ResponseData.out(CodeEnum.SUCCESS,null);
    }

    @GetMapping("/getAllTreeList")
    @ApiOperation("查找所有的菜单树")
    public List<TreeEntity> getAllTreeList() {
        return treeDao.getAllTreeList();
    }

    @GetMapping("/getAllTreeWithUserRight")
    @ApiOperation("获取所有树及按钮，含当前用户是否有权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "用户ID")
    })
    @Override
    public ResponseData getAllTreeWithUserRight(int user_id) {
        Map<String,Object> map = this.treeDao.getAllTreeWithUserRight(user_id);
        return ResponseData.out(CodeEnum.SUCCESS,map);
    }

    @GetMapping("/getAllTreeWithRoleRight")
    @ApiOperation("获取所有树及按钮，含当前用户是否有权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "用户ID")
    })
    @Override
    public ResponseData getAllTreeWithRoleRight(int role_id) {
            Map<String,Object> map = this.treeDao.getAllTreeWithRoleRight(role_id);
        return ResponseData.out(CodeEnum.SUCCESS,map);
    }

    @GetMapping("/getTreeWithUserRight")
    @ApiOperation("根据用户获取权限菜单树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",type = "int",value = "用户id")
    })
    @Override
    public ResponseData getTreeWithUserRight(@RequestParam(name = "user_id") int user_id) {
        List<TreeEntity> treeList = this.treeDao.getTreeWithUserRight(user_id);
        return ResponseData.out(treeList!=null?CodeEnum.SUCCESS:CodeEnum.NORIGHT,treeList);
    }

    @GetMapping("/getTreeByTreeId")
    @ApiOperation("根据菜单ID获取对应的菜单实体")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "tree_id",value = "菜单编号",dataType = "int")
    )
    @Override
    public ResponseData getTreeByTreeId(@RequestParam int tree_id) {
        TreeEntity tree = this.treeDao.getTreeByTreeId(tree_id);
        return ResponseData.out(tree!=null?CodeEnum.SUCCESS:CodeEnum.NOTREE,tree);
    }

    @GetMapping("/getRoleByPage")
    @ApiOperation("分页获取角色列表(del_flag=0)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码（从1开始）", dataType = "int"),
            @ApiImplicitParam(name = "pagesize", value = "分页大小", dataType = "int")
    })
    @Override
    public ResponseData getRoleByPage(int page, int pagesize) {
        MyPager pager = this.roleDao.getRoleByPage(page,pagesize);
        return ResponseData.out(pager!=null?CodeEnum.SUCCESS:CodeEnum.NORECORDS,pager);
    }

    @GetMapping("/getRoleByRoleid")
    @ApiOperation("根据role_id获取角色信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "role_id",value = "角色ID",dataType = "int")
    )
    @Override
    public ResponseData getRoleByRoleid(int role_id) {
        RoleEntity roleEntity = this.roleDao.getRoleByRoleid(role_id);
        return ResponseData.out(roleEntity!=null?CodeEnum.SUCCESS:CodeEnum.NORECORDS,roleEntity);
    }

    @GetMapping("/getRoleTreefuncByUserid")
    @ApiOperation("根据用户获取所有权限按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",value = "用户ID")
    })
    @Override
    public ResponseData getRoleTreefuncByUserid(int user_id) {
        List<RoleTreefuncEntity> list = this.treeDao.getRoleTreefuncByUserid(user_id);
        return ResponseData.out(CodeEnum.SUCCESS,list);
    }

    @PostMapping("/setRoleRight")
    @ApiOperation("设置角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role_id",value = "角色ID",dataType = "int"),
            @ApiImplicitParam(name = "tree_ids", value = "选中的菜单",dataType = "String"),
            @ApiImplicitParam(name = "btni_ds",value = "选中的按钮",dataType = "String")
    })
    @Override
    @Transactional
    public ResponseData setRoleRight(int role_id, String tree_ids,String btn_ids) {
        Gson gson = new Gson();
        List<String> tree = gson.fromJson(tree_ids,new TypeToken<List<String>>(){}.getType());
        List<Map<String,String>> btn = gson.fromJson(btn_ids,new TypeToken<List<Map<String,String>>>(){}.getType());
        boolean result = this.roleDao.setRoleRight(role_id,tree,btn);
        return ResponseData.out(result?CodeEnum.SUCCESS:CodeEnum.FAIL,null);
    }

    @GetMapping("/addOrUpdateRole")
    @ApiOperation("新增或修改角色")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "role",value = "角色实体json字符串")
    )
    @Override
    public ResponseData addOrUpdateRole(String role) {
        //将role转换为实体类
        try{
            RoleEntity roleEntity = new Gson().fromJson(role,RoleEntity.class);
            //非空判断
            if(StringUtils.isEmpty(roleEntity.getRole_name())){
                return ResponseData.out(CodeEnum.ROLENAMEISNULL,null);
            }else {
                boolean result = this.roleDao.addOrUpdateRole(roleEntity);
                return ResponseData.out(result ? CodeEnum.SUCCESS : CodeEnum.FAIL, null);
            }
        }catch (Exception e){
            return ResponseData.out(CodeEnum.FAIL,e.getMessage());
        }
    }

    @GetMapping("/delrole")
    @ApiOperation("删除角色")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "role_id",value = "角色编号")
    )
    @Transactional
    @Override
    public ResponseData delrole(Integer role_id) {
        return ResponseData.out(this.roleDao.delrole(role_id)?CodeEnum.SUCCESS:CodeEnum.FAIL,null);
    }

    @GetMapping("/getArticleKindList")
    @ApiOperation("获取文章分类列表，一二级分类一起拿到前台。不分页。")
    @Override
    public ResponseData getArticleKindList() {
        return ResponseData.out(CodeEnum.SUCCESS,this.articleDao.getArticleKindList());
    }

    @GetMapping("/getArticleKindByKindId")
    @ApiOperation("根据分类id获取分类详情")
    @ApiImplicitParam(name = "kind_id",value = "分类id",required = true,dataType = "int")
    @Override
    public ResponseData getArticleKindByKindId(int kind_id) {
        return ResponseData.out(CodeEnum.SUCCESS,this.articleDao.getArticleKindByKindId(kind_id));

    }

    @GetMapping("/addOrUpdateArticleKind")
    @ApiOperation("新增或修改文章分类提交")
    @ApiImplicitParam(name = "article_kind_str",value = "article_kind表对应的实体json字符串",required = true,dataType = "string")
    @Override
    public ResponseData addOrUpdateArticleKind(String article_kind_str) {
        //转换类型
        try {
            ArticleKindEntity articleKindEntity = new Gson().fromJson(article_kind_str,ArticleKindEntity.class);
            //非空判断
            if(StringUtils.isEmpty(articleKindEntity.getArticle_kind_name())){
                return ResponseData.out(CodeEnum.ARTICLEKINDNAMEISNULL,null);
            }else if(null == articleKindEntity.getSup_article_kind_id()){
                return ResponseData.out(CodeEnum.SUPARTICLEKINDIDISNULL,null);
            }else {
                return ResponseData.out(this.articleDao.addOrUpdateArticleKind(articleKindEntity) ? CodeEnum.SUCCESS : CodeEnum.FAIL, null);
            }
        }catch(Exception ex){
            return ResponseData.out(CodeEnum.FORMATJSONERROR,ex.getMessage());
        }
    }

    @GetMapping("/delArticleKind")
    @ApiOperation("删除文章分类")
    @ApiImplicitParam(name = "kind_id",value = "文章分类id",dataType = "int",required = true)
    @Override
    public ResponseData delArticleKind(int kind_id) {
        try{
            return ResponseData.out(this.articleDao.delArticleKind(kind_id)?CodeEnum.SUCCESS:CodeEnum.FAIL,null);
        }catch(Exception e){
            return ResponseData.out(CodeEnum.FAIL,e.getMessage());
        }
    }

    @GetMapping("/getArticlePage")
    @ApiOperation("分页获取文章列表，可以带搜索条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "当前页码，从1开始",dataType = "int",required = false,defaultValue = "1"),
            @ApiImplicitParam(name = "pagesize",value = "分页大小",defaultValue = "10",dataType = "int",required = false),
            @ApiImplicitParam(name = "keywords",value = "搜索条件：关键词",dataType = "string",required = false),
            @ApiImplicitParam(name = "kind_id",value = "搜索条件：分类id",dataType = "int",required = false)
    })
    @Override
    public ResponseData getArticlePage(int page, int pagesize, String keywords, Integer kind_id) {
        return  ResponseData.out(CodeEnum.SUCCESS,this.articleDao.getArticleByPage(page,pagesize,keywords,kind_id));
    }

    @GetMapping("/getArticleById")
    @ApiOperation("根据文章ID获取文章详情")
    @ApiImplicitParam(name = "article_id",value = "文章id",dataType = "int",required = false)
    @Override
    public ResponseData getArticleById(int article_id) {
        return ResponseData.out(CodeEnum.SUCCESS,this.articleDao.getArticleById(article_id));
    }

    @GetMapping("/addOrUpdateArticle")
    @ApiOperation("新增或修改文章提交")
    @ApiImplicitParam(name = "article_str",value = "article实体对应的jsonstr",required = true,dataType = "string")
    @Override
    public ResponseData addOrUpdateArticle(String article_str) {
        try {
            ArticleEntity articleEntity = new Gson().fromJson(article_str, ArticleEntity.class);
            //必填字段判断
            if (StringUtils.isEmpty(articleEntity.getArticle_title())) {
                return ResponseData.out(CodeEnum.ARTICLETITLEISNULL, null);
            } else if (StringUtils.isEmpty(articleEntity.getArticle_content())) {
                return ResponseData.out(CodeEnum.ARTICLECONTENTISNULL, null);
            } else if (null == articleEntity.getArticle_kind_id()) {
                return ResponseData.out(CodeEnum.ARTICLEKINDIDISNULL, null);
            } else {
                return ResponseData.out(this.articleDao.addOrUpdateArticle(articleEntity)?CodeEnum.SUCCESS:CodeEnum.FAIL,null);
            }
        }catch(Exception ex){
            return ResponseData.out(CodeEnum.FAIL,ex.getMessage());
        }
    }

    @GetMapping("/delArticle")
    @ApiOperation("删除文章")
    @ApiImplicitParam(name = "article_id",value = "文章id",dataType = "int",required = true)
    @Override
    public ResponseData delArticle(Integer article_id) {
        return ResponseData.out(this.articleDao.delArticle(article_id)?CodeEnum.SUCCESS:CodeEnum.FAIL,null);
    }
}
