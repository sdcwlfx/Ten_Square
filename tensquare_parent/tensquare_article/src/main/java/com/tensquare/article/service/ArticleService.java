package com.tensquare.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.pojo.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Map;
import java.util.Set;


//需要先创建实例，所以添加注解
@Service
public class ArticleService {

    //使用持久层DAO，注入
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;//id生成器

    //使用mybatis内置的crud语句，查询所有Article对象，从tb_article表
    public List<Article> findAll(){
        return articleDao.selectList(null);
    }

    //根据id查询文章
    public Article findById(String articleId) {
        return articleDao.selectById(articleId);
    }

    //新增文章
    public void save(Article article) {
        String id=idWorker.nextId()+""; //Long转String
        article.setId(id);

        //初始化数据，浏览量等
        article.setVisits(0);//浏览量
        article.setThumbup(0);//点赞数
        article.setComment(0);//评论数

        //新增
        articleDao.insert(article);


    }

    //修改文章
    public void updateById(Article article) {
        //根据主键修改
        articleDao.updateById(article);

        //根据条件修改
        //创建条件对象
//        EntityWrapper<Article> wrapper=new EntityWrapper<>();
//        wrapper.eq("id",article.getId());
//        articleDao.update(article,wrapper);
    }

    //删除文章
    public void deleteById(String articleId) {
        articleDao.deleteById(articleId);
    }


    /**
     * 分页查询
     * @param map 存储查询条件
     * @param page 页数
     * @param size 每页大小
     * @return
     */
    public Page<Article> findByPage(Map<String, Object> map, Integer page, Integer size) {
        //设置查询条件
        EntityWrapper<Article> wrapper=new EntityWrapper<>();
        Set<String> keySet=map.keySet();
        for(String key:keySet){
//            if(map.get(key)!=null){
//                //当不为空时，表示条件
//                wrapper.eq(key,map.get(key));
//            }
            //第一个参数为真时，将条件加入查询条件中
            //与上面if条件同等效果
            wrapper.eq(map.get(key)!=null,key,map.get(key));

        }

        //设置分页参数,要是用分页对象，必须设置分页拦截器
        Page<Article> pageData=new Page<>(page,size);
        //执行查询
        List<Article> list=articleDao.selectPage(pageData,wrapper);
        pageData.setRecords(list);
        //返回
        return pageData;

    }
}
