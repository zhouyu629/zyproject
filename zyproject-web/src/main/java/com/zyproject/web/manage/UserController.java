package com.zyproject.web.manage;

import com.zyproject.common.MyPager;
import com.zyproject.common.ResponseData;
import com.zyproject.entity.UserEntity;
import com.zyproject.web.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @program: zyproject
 * @description: 用户管理控制器
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-16
 **/
@Controller
@RequestMapping("/manage/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    //用户管理首页
    @RequestMapping("/index")
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int pagesize,
                        @RequestParam(name = "user_name",required = false) String user_name,
                        @RequestParam(required = false) String login_code,
                        @RequestParam(required = false) Integer del_flag,Map<String,Object> map){
        ResponseData responseData = this.userService.getUserList(page,pagesize,user_name,login_code,del_flag);
        map.put("users",responseData.getData());
        return "/manage/user/index";
    }

    //根据user_id查询用户信息
    @RequestMapping("/getuser")
    @ResponseBody
    public ResponseData getuser(@RequestParam int id){
        return this.userService.getUserByUserId(id);
    }

    //校验login_code不重复
    @RequestMapping("/checklogincode")
    @ResponseBody
    public ResponseData checklogincode(@RequestParam(name = "login_code") String login_code){
        return this.userService.findByLoginname(login_code);
    }

    //新增或修改用户提交
    @RequestMapping("/user-submit")
    @ResponseBody
    public ResponseData userSubmit(UserEntity userEntity){
        return this.userService.addOrUpdateUser(userEntity);
    }

    //删除用户
    @RequestMapping("/deluser")
    @ResponseBody
    public ResponseData deluser(Integer user_id){
        return this.userService.deluser(user_id);
    }

    //根据用户id查找用户已分配的角色和未分配的角色列表
    @RequestMapping("/getuserroleinfo")
    @ResponseBody
    public ResponseData getuserroleinfo(int user_id){
        return this.userService.getuserroleinfo(user_id);
    }

    //用户角色设置提交
    @RequestMapping("/userrole-submit")
    @ResponseBody
    public ResponseData userroleSubmit(@RequestParam(name = "user_id") Integer user_id,@RequestParam(name = "roleids",defaultValue = "",required = false) String roleids){
        return this.userService.userroleSubmit(user_id,roleids);
    }
}
