package com.zyproject.service;

import com.zyproject.common.ResponseData;
import com.zyproject.entity.TreeEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ISystemService {

    /**
     *
     *  用户相关
     *
     */

    //用户登录方法
    @RequestMapping("/userLogin")
    public ResponseData userLogin(@RequestParam String user_code, @RequestParam String password);
    //根据用户名，查找用户信息，为SpringSercrity等业务提供服务
    @RequestMapping("/findByLoginname")
    public ResponseData findByLoginname(@RequestParam String login_name);
    //用户列表，可以加user_name,login_code,del_flag等过滤条件
    @RequestMapping("/getUserList")
    public ResponseData getUserList(@RequestParam(name = "page",defaultValue = "1") int page,
                                    @RequestParam(name = "pagesize",defaultValue = "10") int pagesize,
                                    @RequestParam(required = false) String user_name,
                                    @RequestParam(required = false) String login_code,
                                    @RequestParam(required = false) Integer del_flag);
    @RequestMapping("/getUserByUserId")
    public ResponseData getUserByUserId(@RequestParam(name = "user_id") int user_id);

    //新增或修改用户
    @RequestMapping("/addOrUpdateUser")
    public ResponseData addOrUpdateUser(@RequestParam(name = "userStr") String userStr);

    //删除用户(假删除）
    @RequestMapping("/deluser")
    public ResponseData deluser(@RequestParam(name = "user_id") int user_id);

    //根据用户id查询用户已分配角色和未分配角色列表
    @RequestMapping("/getuserroleinfo")
    public ResponseData getuserroleinfo(@RequestParam(name = "user_id") int user_id);

    @RequestMapping("/userroleSubmit")
    public ResponseData userroleSubmit(@RequestParam(name = "user_id") Integer user_id,@RequestParam(name = "roleids",defaultValue = "",required = false) String roleids);


    /**
     *
     *  菜单相关
     *
     */


    //获取所有菜单列表，含有删除未删除的
    @RequestMapping("/getAllTreeList")
    public List<TreeEntity> getAllTreeList();
    //查找所有的树及按钮，含当前用户是否有权限
    @RequestMapping("/getAllTreeWithUserRight")
    public ResponseData  getAllTreeWithUserRight(@RequestParam int user_id);
    //查找所有的树及按钮，含当前用户是否有权限
    @RequestMapping("/getAllTreeWithRoleRight")
    public ResponseData  getAllTreeWithRoleRight(@RequestParam int role_id);
    //根据用户获取有权限的组织机构树
    @RequestMapping("/getTreeWithUserRight")
    public ResponseData getTreeWithUserRight(@RequestParam int user_id);
    //根据tree_id获取菜单实体
    @RequestMapping("/getTreeByTreeId")
    public ResponseData getTreeByTreeId(@RequestParam int tree_id);
    //分页获取角色列表（状态正常）
    @RequestMapping("/getRoleByPage")
    public ResponseData getRoleByPage(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int pagesize);
    //根据role_id获取role
    @RequestMapping("/getRoleByRoleid")
    public ResponseData getRoleByRoleid(@RequestParam int role_id);
    //根据用户获取当前用户所有权限按钮
    @RequestMapping("/getRoleTreefuncByUserid")
    public ResponseData getRoleTreefuncByUserid(@RequestParam  int user_id);
    //设置角色权限
    @PostMapping("/setRoleRight")
    public ResponseData setRoleRight(@RequestParam int role_id,
                                     @RequestParam(name = "tree_ids",required = true) String  tree_ids,
                                     @RequestParam(name = "btn_ids",required = true) String btn_ids);
    //新增或更新role
    @GetMapping("/addOrUpdateRole")
    public ResponseData addOrUpdateRole(@RequestParam String role);

    //删除角色
    @GetMapping("/delrole")
    public ResponseData delrole(@RequestParam Integer role_id);

    /**
     *
     *  文章相关
     *
     */

    //获取文章分类列表，一二级分类一起拿到前台。不分页。
    @GetMapping("/getArticleKindList")
    public ResponseData getArticleKindList();
    //根据分类id获取分类详情
    @GetMapping("/getArticleKindByKindId")
    public ResponseData getArticleKindByKindId(@RequestParam(name = "kind_id") int kind_id);
    //新增或修改文章分类提交
    @GetMapping("/addOrUpdateArticleKind")
    public ResponseData addOrUpdateArticleKind(@RequestParam String article_kind_str);
    //删除文章分类
    @GetMapping("/delArticleKind")
    public ResponseData delArticleKind(@RequestParam int kind_id);
    //分页获取文章列表，可以带搜索条件
    @GetMapping("/getArticlePage")
    public ResponseData getArticlePage(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int pagesize,
                                       @RequestParam(required = false) String keywords,
                                       @RequestParam(required = false) Integer kind_id);
    //根据文章ID获取文章详情
    @GetMapping("/getArticleById")
    public ResponseData getArticleById(@RequestParam int article_id);
    //新增或修改文章提交
    @GetMapping("/addOrUpdateArticle")
    public ResponseData addOrUpdateArticle(@RequestParam String article_str);
    @GetMapping("/delArticle")
    //删除文章
    public ResponseData delArticle(@RequestParam Integer article_id);

}
