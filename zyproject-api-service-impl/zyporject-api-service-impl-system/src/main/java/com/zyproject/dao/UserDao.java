package com.zyproject.dao;

import com.zyproject.common.MyPager;
import com.zyproject.entity.UserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @program: zyproject
 * @description: 用户dao
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-11
 **/
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //用户登录
    public UserEntity userLogin(String user_code, String password){
        String sql = "SELECT * FROM tb_user WHERE login_code=? AND login_password=? AND del_flag=0";
        try {
            List<UserEntity> user = jdbcTemplate.query(sql,new Object[]{user_code,password},new BeanPropertyRowMapper(UserEntity.class));
            if(user!=null){
                //登录次数+1
                jdbcTemplate.update("UPDATE tb_user SET login_count=login_count+1 WHERE login_code=?",user_code);
            }
            return user!=null?user.get(0):null;
        }catch (Exception e){
            return null;
        }
    }

    //根据用户ID查找用户信息
    public UserEntity getUserByUserId(int user_id){
        String sql = "SELECT * FROM tb_user WHERE user_id=?";
        return (UserEntity) this.jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper(UserEntity.class),user_id);
    }

    //新增或修改用户
    public boolean addOrUpdateUser(UserEntity userEntity){
        if(null!=userEntity.getUser_id()){
            //修改
            UserEntity user = this.getUserByUserId(userEntity.getUser_id());
            if(null==user){
                return false;
            }else {
                String sql = "UPDATE tb_user SET user_name=?,login_password=?,mobile=?,last_edit_time=? WHERE user_id=?";
                return jdbcTemplate.update(sql, userEntity.getUser_name(),
                        StringUtils.isNotEmpty(userEntity.getLogin_password()) ? userEntity.getLogin_password() : user.getLogin_password(),
                        userEntity.getMobile(), new Date(), userEntity.getUser_id()) > 0;
            }
        }else{
            //新增
            String sql = "INSERT INTO tb_user(login_code,user_name,login_password,mobile,create_time) VALUES (?,?,?,?,?)";
            return jdbcTemplate.update(sql,
                    userEntity.getLogin_code(),userEntity.getUser_name(),userEntity.getLogin_password(),userEntity.getMobile(),new Date())>0;
        }
    }

    //删除用户
    public boolean deluser(int user_id){
        String sql = "DELETE FROM tb_user WHERE user_id=?";
        int result = this.jdbcTemplate.update(sql,user_id);
        return result>0;
    }

    /**
     * 根据用户id查询用户已分配角色和未分配角色列表
     * @param user_id
     * @return
     */
    public Map<String,Object> getuserroleinfo(int user_id){
        String sql = "SELECT a.role_id,a.role_name FROM tb_role a INNER JOIN tb_user_role b ON b.role_id=a.role_id WHERE b.user_id=?";
        List<Map<String,Object>> userroles = this.jdbcTemplate.queryForList(sql,user_id);
        //未选中的角色
        sql = "SELECT role_id,role_name FROM tb_role WHERE role_id NOT IN (SELECT role_id FROM tb_user_role WHERE user_id=?)";
        List<Map<String,Object>> list = this.jdbcTemplate.queryForList(sql,user_id);
        Map<String,Object> map = new HashMap<String,Object>(){};
        map.put("ulist",userroles);
        map.put("list",list);
        return map;
    }

    /**
     * 用户角色提交
     * @param user_id
     * @param roleids
     * @return
     */
    public boolean userroleSubmit(Integer user_id, String roleids) {
        //1.先删除用户角色
        String sql = "DELETE FROM tb_user_role WHERE user_id=?";
        jdbcTemplate.update(sql,user_id);
        //2. 再重新加入角色
        if(!StringUtils.isEmpty(roleids)){
            sql = "INSERT INTO tb_user_role (role_id,user_id) VALUES (?,?)";
            List<Object[]> list = new ArrayList<>();
            String[] ids = roleids.split(",");
            for (String id :ids) {
                if(!StringUtils.isBlank(id)){
                    list.add(new Object[]{id,user_id});
                }
            }
            int[] result = jdbcTemplate.batchUpdate(sql,list);
        }
        return true;
    }

    //根据登录名，获取用户信息
    public UserEntity findByUsername(String login_name){
        String sql = "SELECT * FROM tb_user WHERE login_code=? AND del_flag=0";
        try {
            List<UserEntity> users = jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserEntity.class),login_name);
            return users!=null?users.get(0):null;
        }catch (Exception ex){
            return null;
        }
    }

    //查询用户列表
    public MyPager getUserList(int page, int pagesize, String user_name, String login_code, Integer del_flag){
        List<Object> queryArgs = new ArrayList<>();
        String countSql = "SELECT COUNT(1) FROM tb_user WHERE 1=1 ";
        String sql = "SELECT * FROM tb_user WHERE 1=1 ";
        if(StringUtils.isNotEmpty(user_name)){
           countSql += " AND user_name like ? ";
           sql += " AND user_name like ? ";
           queryArgs.add("%"+user_name+"%");
        }
        if(StringUtils.isNotEmpty(login_code)){
            countSql += " AND login_code=? ";
            sql += " AND login_code=? ";
            queryArgs.add("login_code");
        }
        if(null!=del_flag){
            countSql += " AND del_flag=? ";
            sql += " AND del_flag=? ";
            queryArgs.add(del_flag);
        }
        int count = jdbcTemplate.queryForObject(countSql,Integer.class);
        //列表需要把分页加进去
        sql += " ORDER BY user_id DESC limit ?,?";
        queryArgs.add((page-1)*pagesize);
        queryArgs.add(pagesize);
        List<UserEntity> users = this.jdbcTemplate.query(sql,queryArgs.toArray(),new BeanPropertyRowMapper(UserEntity.class));
        return new MyPager(page,pagesize,count,users);
    }
}
