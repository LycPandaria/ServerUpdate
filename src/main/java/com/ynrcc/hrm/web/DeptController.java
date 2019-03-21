package com.ynrcc.hrm.web;

import com.ynrcc.hrm.model.Dept;
import com.ynrcc.hrm.service.HrmService;
import com.ynrcc.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeptController {

    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;

    /**
     * 查询部门信息
     * @param pageIndex 页标
     * @param dept 模糊查询参数
     * @param model
     * @return
     */
    @RequestMapping(value = "/dept/select")
    public String selectDept(Integer pageIndex,
                             @ModelAttribute Dept dept,
                             Model model){
        PageModel pageModel = new PageModel();
        if(pageIndex != null)
            pageModel.setPageIndex(pageIndex);
        List<Dept> depts = hrmService.findDept(dept, pageModel);
        model.addAttribute("depts", depts);
        model.addAttribute("pageModel",pageModel);
        return "dept/dept";
    }


    /**
     * 处理删除部门请求
     * @param  ids 需要删除的id字符串
     * @param mv
     * */
    @RequestMapping(value="/dept/delete")
    public ModelAndView removeDept(String ids, ModelAndView mv){
        // 分解id字符串
        String[] idArray = ids.split(",");
        for(String id : idArray){
            // 根据id删除部门
            hrmService.deleteDeptById(Integer.parseInt(id));
        }
        // 设置客户端跳转到查询请求
        mv.setViewName("redirect:/dept/select");
        // 返回ModelAndView
        return mv;
    }

    /**
     * 处理添加请求
     * @param  flag 标记， 1表示跳转到添加页面，2表示执行添加操作
     * @param  dept  要添加的部门对象
     * @param  mv
     * */
    @RequestMapping(value="/dept/add")
    public ModelAndView addDept(
            String flag,
            @ModelAttribute Dept dept,
            ModelAndView mv){
        if(flag.equals("1")){
            // 设置跳转到添加页面
            mv.setViewName("dept/addDept");
        }else{
            // 执行添加操作
            hrmService.addDept(dept);
            // 设置客户端跳转到查询请求
            mv.setViewName("redirect:/dept/select");
        }
        // 返回
        return mv;
    }


    /**
     * 处理修改部门请求
     * @param  flag 标记， 1表示跳转到修改页面，2表示执行修改操作
     * @param  dept 要修改部门的对象
     * @param  mv
     * */
    @RequestMapping(value="/dept/update")
    public ModelAndView updateDpet(
            String flag,
            @ModelAttribute Dept dept,
            ModelAndView mv) {
        if (flag.equals("1")) {
            // 根据id查询部门
            Dept target = hrmService.findDeptById(dept.getId());
            // 设置Model数据
            mv.addObject("dept", target);
            // 设置跳转到修改页面
            mv.setViewName("dept/updateDept");
        } else {
            // 执行修改操作
            hrmService.modifyDept(dept);
            // 设置客户端跳转到查询请求
            mv.setViewName("redirect:/dept/select");
        }
        // 返回
        return mv;
    }
}
