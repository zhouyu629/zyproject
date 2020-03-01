package com.zyproject.web.manage;

import com.zyproject.common.ResponseData;
import com.zyproject.entity.ArticleEntity;
import com.zyproject.entity.ArticleKindEntity;
import com.zyproject.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @program: zyproject
 * @description: 文章控制器
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-28
 **/
@Controller
@RequestMapping("/manage/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    //文章分类管理
    @RequestMapping("/kind")
    public String kind(Map<String,Object> map){
        ResponseData list = this.articleService.getArticleKindList();
        map.put("kindlist",list);
        return "/manage/article/kind";
    }

    //新增或修改文章分类提交
    @RequestMapping("/article_kind_submit")
    @ResponseBody
    public ResponseData addOrUpdateArticleKindSubmit(ArticleKindEntity articleKindEntity){
        return this.articleService.addOrUpdateArticleKindSubmit(articleKindEntity);
    }

    //新增或修改文章
    @RequestMapping("/article_edit")
    public String articleEdit(int article_id){
        return "/manage/article/article_edit";
    }

    //根据文章id获取文章详情
    @RequestMapping("/getarticlebyid")
    @ResponseBody
    public ResponseData getArticleById(int article_kind_id){
        return this.articleService.getArticleKindByKindId(article_kind_id);
    }

    //删除文章分类
    @RequestMapping("/del_article_kind")
    @ResponseBody
    public ResponseData delArticleKind(int article_kind_id){
        return this.articleService.delArticleKind(article_kind_id);
    }

    //文章开始了

    //默认文章首页
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(name = "page",defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pagesize,
                              String keywords, Integer kind_id){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/manage/article/index");
        ResponseData responseData = this.articleService.getArticlePage(page,pagesize,keywords,kind_id);
        mav.addObject("articles",responseData.getData());
        return mav;
    }
    //新增或修改文章界面
    @RequestMapping("/add")
    public String add(Integer article_id,Map<String,Object> map){
        ResponseData responseData = this.articleService.getArticleKindList();
        map.put("kinds",responseData.getData());
        //如果是修改
        ArticleEntity articleEntity = new ArticleEntity();
        if(null!=article_id){
            articleEntity = (ArticleEntity) this.articleService.getArticleById(article_id).getData(ArticleEntity.class);
        }
        map.put("article",articleEntity);
        return "/manage/article/add";
    }

    //新增或修改文章提交
    @RequestMapping("/add-submit")
    @ResponseBody
    public ResponseData addOrSubmitArticle(ArticleEntity articleEntity){
        return this.articleService.addOrUpdateArticle(articleEntity);
    }
}
