package com.zyproject.web.manage;

import com.zyproject.common.CodeEnum;
import com.zyproject.common.ResponseData;
import com.zyproject.common.echarts.*;
import com.zyproject.web.secrity.MyUserDetails;
import com.zyproject.web.service.TreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: zyproject
 * @description: 后台首页
 * @author: zhouyu(zhouyu629 @ qq.com)
 * @create: 2020-02-12
 **/
@Controller
@RequestMapping("/manage")
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TreeService treeService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request,Map<Object,Object> map){
        MyUserDetails userDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.put("username",userDetails.getRealName());

        //权限
        ResponseData data = treeService.getTreeWithUserRight(userDetails.getUserEntity().getUser_id());
        map.put("treelist",data.getData());
        return "manage/index";
    }

    //通用出错页面
    @RequestMapping("/error")
    public String error(HttpServletRequest httpRequest,Map<String,String> resultMap){
        String key = httpRequest.getParameter("key");
        if(key.equals("timeout")){
            //转换成code
            key = String.valueOf(CodeEnum.TIMEOUT.getCode());
        }
        if("1002".equals(key)) {
            //这是SpringSecurity错误，从session里拿最后一次错误信息
            HttpSession session = httpRequest.getSession();
            BadCredentialsException errorMsg = (BadCredentialsException) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            logger.info("error:" + errorMsg.getLocalizedMessage());
            resultMap.put("errorDesc",errorMsg.getLocalizedMessage());
        }else{
            resultMap.put("errorDesc",CodeEnum.getEnumDesc(Integer.valueOf(key)));
        }
        return "manage/error";
    }

    //默认首页
    @RequestMapping("/home")
    public String home(Map<Object,Object> map){
        MyUserDetails userDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.put("user",userDetails.getUserEntity());
        return "manage/home";
    }
    @RequestMapping("/getecharts-data")
    @ResponseBody
    public ResponseData getEchartsData(){
        //构造一个echarts送前台
        //EnhancedOption option = new EnhancedOption();
        EOption option = new EOption();
        ETitle title = new ETitle();
        title.setText("测试");
        option.setTitle(title);

        //x轴
        Object[] obj = new Object[]{1,2,3,4,5,6,7,8,9,10,11,12}; //测试1-12月
        EXaixs xaixs = new EXaixs(obj);
        xaixs.setName("月份");
        xaixs.setNameLocation("end");
        ELabel label = new ELabel(true,"{a}月",null);
        xaixs.setLabel(label);
        option.setXAxis(xaixs);

        //y轴;
        EYaxis yaxis = new EYaxis(null,"value");
        yaxis.setName("销售额");
        yaxis.setNameLocation("center");
        yaxis.setNameGap(40);
        option.setYAxis(yaxis);

        //数据序列
        obj = new Object[]{50,40,80,90,100,10,15,20,11,12,120,110};
        ESeries series = new ESeries("销售额（万元）","line",obj,true);
        List<ESeries> list = new ArrayList<>();
        list.add(series);
        option.setSeries(list);

        //tooltip
        option.setTooltip(new ETooltip());

        //leged
        option.setLegend(new ELegend("plain",true));

        return ResponseData.out(CodeEnum.SUCCESS,option);
    }
}
