package com.zyproject.entity;

import lombok.Data;

import java.util.Date;

/**
 * @program: zyproject
 * @description: 文章实体类
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-28
 **/
@Data
public class ArticleEntity {
    private Integer article_id;
    private Integer article_kind_id;
    private String article_title;
    private String article_content;
    private Date add_date;
    private Integer read_count;
}
