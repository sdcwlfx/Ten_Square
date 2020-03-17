package com.tensquare.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.pojo.Channel;

//使用mybatisplus内置的CRUD方法,指定了Article数据类型与tb_article表对应
public interface ArticleDao extends BaseMapper<Article> {

}
