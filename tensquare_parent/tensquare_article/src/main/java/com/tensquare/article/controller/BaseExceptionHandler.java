package com.tensquare.article.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公共处理异常类
 */

//注解表明异常处理类
@ControllerAdvice
public class BaseExceptionHandler {
    //声明要捕捉的异常(接收的异常)
    @ExceptionHandler(Exception.class)
    //处理结果转为JSON
    @ResponseBody
    public Result handler(Exception e){
        System.out.println("处理异常");
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }

}
