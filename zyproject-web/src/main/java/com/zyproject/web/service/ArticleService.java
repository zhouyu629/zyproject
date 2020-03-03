package com.zyproject.web.service;

import com.google.gson.Gson;
import com.zyproject.common.CodeEnum;
import com.zyproject.common.ResponseData;
import com.zyproject.entity.ArticleEntity;
import com.zyproject.entity.ArticleKindEntity;
import com.zyproject.web.feignclient.SystemFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: zyproject
 * @description: 文章WEB层服务
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-28
 **/
@Service
public class ArticleService {
    @Autowired
    private SystemFeign systemFeign;

    //获取文章分类列表
    public ResponseData getArticleKindList(){
        return this.systemFeign.getArticleKindList();
    }
    //新增或修改文章分类提交
    public ResponseData addOrUpdateArticleKindSubmit(ArticleKindEntity articleKindEntity){
        //转换为jsonstr
        String article_kind = new Gson().toJson(articleKindEntity);
        return this.systemFeign.addOrUpdateArticleKind(article_kind);
    }

    //根据kindid获取kind详情
    public ResponseData getArticleKindByKindId(int article_kind_id){
        return this.systemFeign.getArticleKindByKindId(article_kind_id);
    }

    //删除文章分类
    public ResponseData delArticleKind(int article_kind_id){
        return systemFeign.delArticleKind(article_kind_id);
    }

    //分页获取文章列表
    public ResponseData getArticlePage(Integer page, Integer pagesize, String keywords, Integer kind_id){
        return systemFeign.getArticlePage(page,pagesize,keywords,kind_id);
    }

    //根据文章ID获取文章详情
    public ResponseData getArticleById(int article_id){
        return this.systemFeign.getArticleById(article_id);
    }

    //新增或修改文章提交
    public ResponseData addOrUpdateArticle(ArticleEntity articleEntity){
        try{
            return systemFeign.addOrUpdateArticle(new Gson().toJson(articleEntity));
        }catch (Exception e){
            return ResponseData.out(CodeEnum.FAIL,e.getMessage());
        }
    }

    //删除文章
    public ResponseData delArticle(Integer article_id){
        return  this.systemFeign.delArticle(article_id);
    }
}
