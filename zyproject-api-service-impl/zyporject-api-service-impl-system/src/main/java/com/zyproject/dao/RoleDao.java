package com.zyproject.dao;

import com.zyproject.common.MyPager;
import com.zyproject.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @program: zyproject
 * @description: 角色dao
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-16
 **/
@Repository
public class RoleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 分页获取角色列表
     * @param page：当前页码，从1开始
     * @param pagesize：分页大小
     * @return
     */
    public MyPager getRoleByPage(int page,int pagesize){
        //totalrecords
        String countSql = "SELECT count(1) FROM tb_role WHERE del_flag=0";
        int totalRecords = jdbcTemplate.queryForObject(countSql,Integer.class);
        String sql = "SELECT * FROM tb_role WHERE del_flag=0 ORDER BY taxis ASC limit ?,?";
        List<RoleEntity> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(RoleEntity.class),(page-1)*pagesize,pagesize);
        return new MyPager(page,pagesize,totalRecords,list);
    }

    /**
     * 根据role_id获取role
     * @param role_id
     * @return
     */
    public RoleEntity getRoleByRoleid(int role_id){
        String sql = "SELECT * FROM tb_role WHERE role_id=?";
        return (RoleEntity) jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper(RoleEntity.class),role_id);
    }

    /**
     * 角色权限设置
     * @param role_id
     * @param tree_ids
     * @param btn_ids
     * @return
     */
    public boolean setRoleRight(int role_id,List<String> tree_ids,List<Map<String,String>> btn_ids){
        //1. 删除tb_role_treefunc
        String sql = "DELETE FROM tb_role_treefunc WHERE role_id="+role_id;
        jdbcTemplate.execute(sql);
        //2. 删除tb_role_right
        sql = "DELETE FROM tb_role_right WHERE role_id="+role_id;
        jdbcTemplate.execute(sql);
        //要考虑下，有可能是把所有权限删除，没有加入任何新的权限进来
        if(tree_ids.size()>0 && btn_ids.size()>0) {
            //4. 重新加入tb_role_right
            sql = "INSERT INTO tb_role_right(role_id,tree_id) VALUES (?,?)";
            List<Object[]> list = new ArrayList<>();
            for (String tree_id : tree_ids) {
                list.add(new Object[]{role_id,tree_id});
            }
            jdbcTemplate.batchUpdate(sql, list);
            //3. 重新加入tb_role_treefunc
           sql = "INSERT INTO tb_role_treefunc(role_id,tree_id,tree_func_name) VALUES (?,?,?)";
           list = new ArrayList<>();
            for (Map<String, String> map : btn_ids) {
                list.add(new Object[]{role_id, map.get("tree_id"), map.get("btn_id")});
            }
            jdbcTemplate.batchUpdate(sql,list);
        }

        return true;
    }

    /**
     * 更新或新增角色
     * @param role
     * @return
     */
    public boolean addOrUpdateRole(RoleEntity role){
        if(null != role.getRole_id()){
            //这是编辑
            String sql = "UPDATE tb_role SET role_name=?,taxis=?,last_edit_time=? WHERE role_id=?";
            int result = jdbcTemplate.update(sql,role.getRole_name(),role.getTaxis(),new Date(),role.getRole_id());
            return result>0;
        }else{
            //新增
            String sql = "INSERT INTO tb_role (role_name,taxis,del_flag,create_time) VALUES (?,?,?,?)";
            int result = jdbcTemplate.update(sql,role.getRole_name(),role.getTaxis(),0,new Date());
            return result > 0;
        }
    }

    /**
     * 删除角色
     * @param role_id
     * @return
     */
    public boolean delrole(Integer role_id){
        //1. 删除所有已分配角色按钮权限tb_role_treefunc
        String sql = "DELETE FROM tb_role_treefunc WHERE role_id="+role_id;
        //2. 删除所有已分配权限tb_role_right
        String sql2 = "DELETE FROM tb_role_right WHERE role_id="+role_id;
        //3. 删除已分配角色tb_user_role
        String sql3 = "DELETE FROM tb_user_role WHERE role_id="+role_id;
        //4. 删除角色
        String sql4 = "DELETE FROM tb_role WHERE role_id="+role_id;
        int[] result = jdbcTemplate.batchUpdate(sql,sql2,sql3,sql4);
        return true;
    }
}
