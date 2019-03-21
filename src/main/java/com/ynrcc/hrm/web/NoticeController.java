package com.ynrcc.hrm.web;

import com.ynrcc.hrm.model.Notice;
import com.ynrcc.hrm.model.User;
import com.ynrcc.hrm.service.HrmService;
import com.ynrcc.hrm.util.common.Constants;
import com.ynrcc.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NoticeController {
    /**
     * 自动注UserService
     * */
    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;


    @RequestMapping(value="/notice/select")
    public String selectNotice(Model model, Integer pageIndex,
                               @ModelAttribute Notice notice){
        PageModel pageModel = new PageModel();
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        List<Notice> notices = hrmService.findNotice(notice, pageModel);
        model.addAttribute("notices", notices);
        model.addAttribute("pageModel", pageModel);
        return "notice/notice";

    }

    /**
     * 处理添加请求
     * @param  id  要显示的公告id
     * @param  model
     * */
    @RequestMapping(value="/notice/preview")
    public String previewNotice(
            Integer id,Model model){

        Notice notice = hrmService.findNoticeById(id);
        User creater = notice.getUser();
        model.addAttribute("notice", notice);
        // 返回
        return "notice/preview";
    }

    /**
     * 处理删除公告请求
     * @param  ids 需要删除的id字符串
     * @param  mv
     * */
    @RequestMapping(value="/notice/delete")
    public ModelAndView removeNotice(String ids, ModelAndView mv){
        // 分解id字符串
        String[] idArray = ids.split(",");
        for(String id : idArray){
            // 根据id删除公告
            hrmService.removeNoticeById(Integer.parseInt(id));
        }
        // 设置客户端跳转到查询请求
        mv.setViewName("redirect:/notice/select");
        // 返回ModelAndView
        return mv;
    }

    /**
     * 处理添加请求
     * @param  flag 标记， 1表示跳转到添加页面，2表示执行添加操作
     * @param notice  要添加的公告对象
     * @param  mv
     * */
    @RequestMapping(value="/notice/add")
    public ModelAndView addNotice(
            String flag,
            @ModelAttribute Notice notice,
            ModelAndView mv,
            HttpSession session){
        if(flag.equals("1")){
            mv.setViewName("notice/addNotice");
        }else{
            User user = (User) session.getAttribute(Constants.USER_SESSION);
            notice.setUser(user);
            hrmService.addNotice(notice);
            mv.setViewName("redirect:/notice/select");
        }
        // 返回
        return mv;
    }

    /**
     * 处理添加请求
     * @param  flag 标记， 1表示跳转到修改页面，2表示执行修改操作
     * @param notice  要添加的公告对象
     * @param  mv
     * */
    @RequestMapping(value="/notice/update")
    public ModelAndView updateNotice(
            String flag,
            @ModelAttribute Notice notice,
            ModelAndView mv,
            HttpSession session){
        if(flag.equals("1")){
            Notice target = hrmService.findNoticeById(notice.getId());
            mv.addObject("notice",target);
            mv.setViewName("notice/updateNotice");
        }else{
            hrmService.modifyNotice(notice);
            mv.setViewName("redirect:/notice/select");
        }
        // 返回
        return mv;
    }


}
