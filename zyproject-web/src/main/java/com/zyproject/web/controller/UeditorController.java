package com.zyproject.web.controller;

import com.baidu.ueditor.ActionEnter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: zyproject
 * @description: Ueditor配置控制器
 * @author: zhouyu(zhouyu629 # qq.com)
 * @create: 2020-02-29
 **/
@Controller
public class UeditorController {

    @Value("${filePath}")
    private String filePath;

    @RequestMapping("/ueconfig")
    public void config(String action,HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        //String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String rootPath = filePath;
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            //解决ueditor 列出文件或图片，会把rootpapth加上的问题
            if( action!=null &&
                    (action.equals("listfile") || action.equals("listimage") ) ){
                rootPath = rootPath.replace("\\\\", "/");
                exec = exec.replaceAll(rootPath, "");
            }
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
