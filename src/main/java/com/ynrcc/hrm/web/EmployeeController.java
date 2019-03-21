package com.ynrcc.hrm.web;

import com.ynrcc.hrm.model.Dept;
import com.ynrcc.hrm.model.Employee;
import com.ynrcc.hrm.model.Job;
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
public class EmployeeController {
    /**
     * 自动注入hrmService
     * */
    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;

    /**
     * 处理查询请求
     * @param pageIndex 请求的是第几页
     * @param  job_id 职位编号
     * @param  dept_id 部门编号
     * @param employee 模糊查询参数
     * @param  model
     * */
    @RequestMapping(value="/employee/select")
    public String selectEmployee(Integer pageIndex,
                                 Integer job_id,Integer dept_id,
                                 @ModelAttribute Employee employee,
                                 Model model){
        // 模糊查询时判断是否有关联对象传递，如果有，创建并封装关联对象
        this.genericAssociation(job_id, dept_id, employee);
        // 创建分页对象
        PageModel pageModel = new PageModel();
        // 如果参数pageIndex不为null，设置pageIndex，即显示第几页
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        // 查询职位信息，用于模糊查询
        List<Job> jobs = hrmService.findAllJob();
        // 查询部门信息 ，用于模糊查询
        List<Dept> depts = hrmService.findAllDept();
        // 查询外包信息
        List<Employee> employees = hrmService.findEmployee(employee,pageModel);
        // 设置Model数据
        // addAttribute 对应前端的 requestScope.jobs
        model.addAttribute("employees", employees);
        model.addAttribute("jobs", jobs);
        model.addAttribute("depts", depts);
        model.addAttribute("pageModel", pageModel);
        // 返回外包页面
        return "employee/employee";

    }

    /**
     * 处理添加外包请求
     * @param  flag 标记， 1表示跳转到添加页面，2表示执行添加操作
     * @param  job_id 职位编号
     * @param dept_id 部门编号
     * @param  employee 接收添加参数
     * @param  mv
     * */
    @RequestMapping(value="/employee/add")
    public ModelAndView addEmployee(
            String flag,
            Integer job_id,Integer dept_id,
            @ModelAttribute Employee employee,
            ModelAndView mv){
        if(flag.equals("1")){
            // 查询职位信息
            List<Job> jobs = hrmService.findAllJob();
            // 查询部门信息
            List<Dept> depts = hrmService.findAllDept();
            // 设置Model数据
            mv.addObject("jobs", jobs);
            mv.addObject("depts", depts);
            // 返回添加外包页面
            mv.setViewName("employee/addEmployee");
        }else{
            // 判断是否有关联对象传递，如果有，创建关联对象
            this.genericAssociation(job_id, dept_id, employee);
            // 添加操作
            hrmService.addEmployee(employee);
            // 设置客户端跳转到查询请求
            mv.setViewName("redirect:/employee/select");
        }
        // 返回
        return mv;

    }

    /**
     * 处理删除外包请求
     * @param  ids 需要删除的id字符串
     * @param  mv
     * */
    @RequestMapping(value="/employee/delete")
    public ModelAndView removeEmployee(String ids,ModelAndView mv){
        // 分解id字符串
        String[] idArray = ids.split(",");
        for(String id : idArray){
            // 根据id删除外包
            hrmService.removeEmployeeById(Integer.parseInt(id));
        }
        // 设置客户端跳转到查询请求
        mv.setViewName("redirect:/employee/select");
        // 返回ModelAndView
        return mv;
    }

    /**
     * 处理修改外包请求
     * @param flag 标记，1表示跳转到修改页面，2表示执行修改操作
     * @param job_id 职位编号
     * @param dept_id 部门编号
     * @param employee  要修改外包的对象
     * @param mv
     * */
    @RequestMapping(value="/employee/update")
    public ModelAndView updateEmployee(
            String flag,
            Integer job_id,Integer dept_id,
            @ModelAttribute Employee employee,
            ModelAndView mv){
        if(flag.equals("1")){
            // 根据id查询外包
            Employee target = hrmService.findEmployeeById(employee.getId());
            // 需要查询职位信息
            List<Job> jobs = hrmService.findAllJob();
            // 需要查询部门信息
            List<Dept> depts = hrmService.findAllDept();
            // 设置Model数据
            mv.addObject("jobs", jobs);
            mv.addObject("depts", depts);
            mv.addObject("employee", target);
            // 返回修改外包页面
            mv.setViewName("employee/updateEmployee");
        }else{
            // 创建并封装关联对象
            this.genericAssociation(job_id, dept_id, employee);
            System.out.println("updateEmployee -->> " + employee);
            // 执行修改操作
            hrmService.modifyEmployee(employee);
            // 设置客户端跳转到查询请求
            mv.setViewName("redirect:/employee/select");
        }
        // 返回
        return mv;
    }

    /**
     * 由于部门和职位在Employee中是对象关联映射，
     * 所以不能直接接收参数，需要创建Job对象和Dept对象
     * */
    private void genericAssociation(Integer job_id,
                                    Integer dept_id,Employee employee){
        if(job_id != null){
            Job job = new Job();
            job.setId(job_id);
            employee.setJob(job);
        }
        if(dept_id != null){
            Dept dept = new Dept();
            dept.setId(dept_id);
            employee.setDept(dept);
        }
    }

}
