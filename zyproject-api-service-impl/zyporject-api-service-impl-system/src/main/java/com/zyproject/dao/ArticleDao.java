package com.zyproject.dao;

import com.zyproject.common.MyPager;
import com.zyproject.entity.ArticleEntity;
import com.zyproject.entity.ArticleKindEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: zyproject
 * @description: 文章DAO
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-28
 **/
@Repository
public class ArticleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //获取分类清单，按一级类排序
    public List<ArticleKindEntity> getArticleKindList(){
        String sql = "SELECT * FROM tb_article_kind ORDER BY sup_article_kind_id ASC,taxis ASC ";
        List<ArticleKindEntity> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ArticleKindEntity.class));
        return list;
    }

    //新增或修改分类
    public boolean addOrUpdateArticleKind(ArticleKindEntity articleKindEntity){
        if(null!=articleKindEntity.getArticle_kind_id()){
            //修改
            String sql = "UPDATE tb_article_kind SET article_kind_name=?,taxis=?  WHERE article_kind_id=?";
            return jdbcTemplate.update(sql,articleKindEntity.getArticle_kind_name(),articleKindEntity.getTaxis(),articleKindEntity.getArticle_kind_id())>0;
        }else{
            //新增
            String sql = "INSERT INTO tb_article_kind (article_kind_name,sup_article_kind_id,taxis) VALUES (?,?,?)";
            return jdbcTemplate.update(sql,articleKindEntity.getArticle_kind_name(),articleKindEntity.getSup_article_kind_id(),articleKindEntity.getTaxis())>0;
        }
    }

    //删除文章分类
    public boolean delArticleKind(int article_kind_id){
        //1. 删除所有一级、二级分类下所有文章
        String sql = "DELETE FROM tb_article WHERE article_kind_id IN (SELECT article_kind_id FROM tb_article_kind WHERE article_kind_id=? OR sup_article_kind_id=?)";
        jdbcTemplate.update(sql,article_kind_id,article_kind_id);
        //2. 删除所有一级、二级分类
        sql = "DELETE FROM tb_article_kind WHERE article_kind_id=? OR sup_article_kind_id=?";
        jdbcTemplate.update(sql,article_kind_id,article_kind_id);
        return true;
    }

    //根据kindid取详情
    public ArticleKindEntity getArticleKindByKindId(int kind_id){
        String sql = "SELECT * FROM tb_article_kind WHERE article_kind_id=?";
        List<ArticleKindEntity> list = this.jdbcTemplate.query(sql,new BeanPropertyRowMapper(ArticleKindEntity.class),kind_id);
        return list.size()>0?list.get(0):null;
    }
    /**
     * 分页获取角色列表
     * @param page：当前页码，从1开始
     * @param pagesize：分页大小
     * @param keywords
     * @param  kind_id
     * @return
     */
    public MyPager getArticleByPage(Integer page, Integer pagesize,String keywords,Integer kind_id){
        List<Object> queryArgs = new ArrayList<>();
        //totalrecords
        String countSql = "SELECT count(1) FROM tb_article WHERE 1=1 ";
        String sql = "SELECT * FROM tb_article WHERE 1=1 ";
        if(StringUtils.isNotEmpty(keywords)){
            countSql += " AND article_title like  ?";
            sql += " AND article_title like  ?";
            queryArgs.add("%"+keywords+"%");
        }
        if(null!=kind_id){
            countSql += " AND article_kind_id=?";
            sql += " AND article_kind_id=?";
            queryArgs.add(kind_id);
        }
        sql += " ORDER BY add_date DESC limit ?,?";
        int totalRecords = jdbcTemplate.queryForObject(countSql,queryArgs.toArray(),Integer.class);
        queryArgs.add((page-1)*pagesize);
        queryArgs.add(pagesize);
        List<ArticleEntity> list = jdbcTemplate.query(sql,queryArgs.toArray(),new BeanPropertyRowMapper(ArticleEntity.class));
        return new MyPager(page,pagesize,totalRecords,list);
    }

    //根据文章ID获取文章详情
    public ArticleEntity getArticleById(int article_id){
        String sql = "SELECT * FROM tb_article WHERE article_id=?";
        return (ArticleEntity) jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper(ArticleEntity.class),article_id);
    }

    //新增或修改文章提交
    public boolean addOrUpdateArticle(ArticleEntity articleEntity){
        String sql = "";
        if(null!=articleEntity.getArticle_id()){
            //修改
            sql = "UPDATE tb_article SET article_kind_id=?,article_title=?,article_content=? WHERE article_id=?";
            return jdbcTemplate.update(sql,articleEntity.getArticle_kind_id(),articleEntity.getArticle_title(),
                    articleEntity.getArticle_content(),articleEntity.getArticle_id())>0;
        }else{
            //新增
            sql = "INSERT INTO tb_article (article_kind_id,article_title,article_content,add_date) VALUES (?,?,?,?)";
            return jdbcTemplate.update(sql,articleEntity.getArticle_kind_id(),articleEntity.getArticle_title(),
                    articleEntity.getArticle_content(),new Date())>0;
        }
    }

    //删除文章
    public boolean delArticle(Integer article_id){
        String sql = "DELETE FROM tb_article WHERE article_id=?";
        return this.jdbcTemplate.update(sql,article_id)>0;
    }
}
