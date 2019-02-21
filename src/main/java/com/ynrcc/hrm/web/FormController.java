package com.ynrcc.hrm.web;

import com.ynrcc.hrm.util.common.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转页面控制器
 */
@Controller
public class FormController {

    //@PathVariable可以用来映射URL中的占位符到目标方法的参数中
    @RequestMapping(value = "/{formName}")
    public String form(@PathVariable String formName){
        return formName;
    }

}
