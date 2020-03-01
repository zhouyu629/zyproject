package com.zyproject.dao;

import com.zyproject.entity.RoleTreefuncEntity;
import com.zyproject.entity.TreeEntity;
import com.zyproject.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zyproject
 * @description: 系统功能菜单树DAO
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-10
 **/
@Repository
public class TreeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //获取所有菜单（含删除的）
    public List<TreeEntity> getAllTreeList(){
        String sql = "SELECT * FROM tb_tree";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(TreeEntity.class));
    }

    //获取所有树及按钮，含当前用户是否有权限
    public Map<String,Object> getAllTreeWithUserRight(int user_id){
        String sql = "SELECT a.tree_id,a.tree_name,a.sup_tree_id,a.taxis,IFNULL(b.tree_id,0) as perm FROM  " +
                " tb_tree a left JOIN  " +
                "(SELECT DISTINCT rr.tree_id FROM tb_user_role ur INNER JOIN tb_role_right rr ON rr.role_id=ur.role_id  " +
                "WHERE ur.user_id=?) b " +
                "ON b.tree_id=a.tree_id " +
                " WHERE a.del_flag=0 AND enable_flag=1 " +
                " ORDER BY sup_tree_id ASC,taxis ASC;";
        List<Map<String,Object>> tree = this.jdbcTemplate.queryForList(sql,user_id);
        //再查询所有按钮
        sql = "SELECT a.tree_id,b.tree_func_name,b.tree_func_namedesc,IFNULL(c.tree_id,0) as perm FROM tb_tree a INNER JOIN tb_tree_func b ON b.tree_id=a.tree_id " +
                "left JOIN  " +
                "(SELECT rt.tree_id,rt.role_id,rt.tree_func_name FROM tb_role_treefunc rt INNER JOIN tb_user_role ur  " +
                "ON ur.role_id=rt.role_id WHERE ur.user_id=?) c " +
                "ON c.tree_id=a.tree_id AND c.tree_func_name=b.tree_func_name";
        List<Map<String,Object>> func = jdbcTemplate.queryForList(sql,user_id);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("tree",tree);
        map.put("func",func);
        return map;
    }

    //获取所有树及按钮，含当前角色是否有权限
    public Map<String,Object> getAllTreeWithRoleRight(int role_id){
        String sql = "SELECT a.tree_id,a.tree_name,a.sup_tree_id,a.taxis,IFNULL(b.tree_id,0) as perm FROM  " +
                " tb_tree a left JOIN  " +
                "(SELECT rr.tree_id FROM tb_role_right rr  " +
                "WHERE rr.role_id=?) b " +
                "ON b.tree_id=a.tree_id " +
                " WHERE a.del_flag=0 AND enable_flag=1 " +
                " ORDER BY sup_tree_id ASC,taxis ASC;";
        List<Map<String,Object>> tree = this.jdbcTemplate.queryForList(sql,role_id);
        //再查询所有按钮
        sql = "SELECT a.tree_id,b.tree_func_name,b.tree_func_namedesc,IFNULL(c.tree_id,0) as perm FROM tb_tree a INNER JOIN tb_tree_func b ON b.tree_id=a.tree_id " +
                "left JOIN  " +
                "(SELECT rt.tree_id,rt.role_id,rt.tree_func_name FROM tb_role_treefunc rt WHERE rt.role_id=?) c " +
                "ON c.tree_id=a.tree_id AND c.tree_func_name=b.tree_func_name";
        List<Map<String,Object>> func = jdbcTemplate.queryForList(sql,role_id);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("tree",tree);
        map.put("func",func);
        return map;
    }

    /**
     *  根据用户获取有权限的菜单树
     * @param user_id 用户ID
     * @return
     */
    public List<TreeEntity> getTreeWithUserRight(int user_id){
        String sql = "SELECT DISTINCT a.* FROM tb_tree a INNER JOIN tb_role_right b ON b.tree_id=a.tree_id INNER JOIN tb_user_role c ON " +
                " c.role_id=b.role_id WHERE a.del_flag=0 AND a.enable_flag=1 AND c.user_id=? order by a.sup_tree_id ASC,a.taxis ASC;";
        return  jdbcTemplate.query(sql,new BeanPropertyRowMapper(TreeEntity.class),user_id);
    }

    /**
     * 根据treeid获取菜单实体
     * @param tree_id 菜单编码
     * @return
     */
    public TreeEntity getTreeByTreeId(int tree_id){
        String sql = "SELECT * FROM tb_tree WHERE tree_id=?";
        return (TreeEntity) jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper(TreeEntity.class),tree_id);
    }


    /**
     * 根据用户，返回当前用户所有按钮权限
     * @param user_id 用户ID
     * @return
     */
    public List<RoleTreefuncEntity> getRoleTreefuncByUserid(int user_id){
        String sql = "SELECT DISTINCT a.* FROM tb_role_treefunc a INNER JOIN tb_role_right b ON b.role_id=a.role_id " +
                " INNER JOIN tb_user_role c ON c.role_id=b.role_id WHERE c.user_id=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(RoleTreefuncEntity.class),user_id);
    }
}
