package com.ynrcc.hrm.service;

import com.ynrcc.hrm.dao.*;
import com.ynrcc.hrm.model.*;
import com.ynrcc.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 外包人员管理服务层
 */
// Propagation.REQUIRED 属性指有事务就处于当前事务中，没有事务就创建一个事务
// Isolation.DEFAULT 属性表示使用事务数据库的默认隔离级别
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
@Service("hrmService")
public class HrmService {

    // 自动注入 Dao 层对象
    @Autowired
    private UserDao userDao;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private DocumentDao documentDao;

    /**
     * 登陆
     * @param loginname
     * @param password
     * @return 对应的用户
     */
    public User login(String loginname, String password){
        System.out.println("HrmService login -- >>");
        return userDao.getByLoginnameAndPassword(loginname,password);
    }

}
