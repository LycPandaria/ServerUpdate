package com.ynrcc.hrm.web;

import com.ynrcc.hrm.model.User;
import com.ynrcc.hrm.service.HrmService;
import com.ynrcc.hrm.util.common.Constants;
import com.ynrcc.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    /**
     * 查询用户
     * @param pageIndex 页标
     * @param user 模糊查询参数
     * @param model
     * @return 用户列表页面
     */
    @RequestMapping(value = "/user/select")
    public String selectUser(Integer pageIndex, @ModelAttribute User user, Model model){
        PageModel pageModel = new PageModel();
        if(null != pageIndex)
            pageModel.setPageIndex(pageIndex);
        // 查询用户信息
        List<User> users = hrmService.findUser(user, pageModel);
        model.addAttribute("users", users);
        model.addAttribute("pageModel",pageModel);
        return "user/user";
    }

    /**
     * 删除用户
     * @param ids 需要删除的 ids，用逗号隔开
     * @param mv
     * @return
     */
    @RequestMapping(value = "/user/delete")
    public ModelAndView removeUser(String ids, ModelAndView mv){
        // 分解 id 字符串
        String[] idArr = ids.split(",");
        for(String id : idArr){
            // 根据 id 删除用户
            hrmService.deleteUserById(Integer.parseInt(id));
        }
        // 设置跳转
        mv.setViewName("redirect:/user/select");
        return mv;
    }



}
