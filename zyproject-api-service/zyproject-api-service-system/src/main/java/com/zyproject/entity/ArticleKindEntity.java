package com.zyproject.entity;

import lombok.Data;

/**
 * @program: zyproject
 * @description: 文章分类实体
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-28
 **/
@Data
public class ArticleKindEntity {
    private Integer article_kind_id;
    private String article_kind_name;
    private Integer sup_article_kind_id;
    private Integer taxis;
}
