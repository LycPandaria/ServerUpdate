package com.ynrcc.hrm.web;

import com.ynrcc.hrm.model.User;
import com.ynrcc.hrm.service.HrmService;
import com.ynrcc.hrm.util.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 用户 Controller 类
 */
@Controller
public class UserController {

    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;

    /**
     * 登陆请求
     * @param loginname 登录名
     * @param password 密码
     * @return 调整的视图
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(@RequestParam("loginname") String loginname,
                              @RequestParam("password") String password,
                              HttpSession sesison, ModelAndView mv){
        User user = hrmService.login(loginname, password);
        if(null != user){
            // 保存用户到 session 中
            sesison.setAttribute(Constants.USER_SESSION, user);
            mv.setViewName("redirect:/main");   // 跳转到 main 界面
        }else {
            // 设置登陆失败信息
            mv.addObject("message","登录名或密码错误！");
            mv.setViewName("forward:/loginForm");
        }
        return mv;
    }
}
