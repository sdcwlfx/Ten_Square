package com.tensquare.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.pojo.Channel;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//相当于添加了Controller、ResponseBody注解
@RestController
//只响应“article”开始的请求
@RequestMapping("article")
//跨域处理
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //GET /article/ 文章全部内容
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Article> list=articleService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    //GET /article/{articleId} 根据ID查询文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String articleId){ //注解获取传入的路径变量
        Article article=articleService.findById(articleId);
        return new Result(true, StatusCode.OK,"查询成功",article);

    }

    //POST /article 增加文章
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){ //注解@RequestBody将上传的JOSN格式内容转为Article对象
        articleService.save(article);
        return new Result(true,StatusCode.OK,"新增成功");
    }


    //PUT /article/{articleId} 修改文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String articleId,@RequestBody Article article){
        //设置id
        article.setId(articleId);
        //执行修改
        articleService.updateById(article);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    //DELETE /article/{articleId} 删除文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String articleId){
        articleService.deleteById(articleId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    //POST /article/search/{page}/{size} 分页条件查询
    @RequestMapping(value = "search/{page}/{size}",method = RequestMethod.POST)
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer size,
                             @RequestBody Map<String,Object> map){
        //根据条件分页查询，map为传入的所有条件
        Page<Article> pageData=articleService.findByPage(map,page,size);
        //封装分页返回对象
        PageResult<Article> pageResult=new PageResult<Article>(pageData.getTotal(),pageData.getRecords());
        //返回查询结果表
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    //测试公共异常处理
    @RequestMapping(value = "exception",method = RequestMethod.GET)
    public Result test(){
        int a=1/0;
        return null;
    }



}
