package com.ynrcc.hrm.service;

import com.ynrcc.hrm.dao.*;
import com.ynrcc.hrm.model.*;
import com.ynrcc.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //指定事务是否为只读事务，默认值为 false；为了忽略那些不需要事务的方法，比如读取数据，可以设置 read-only 为 true。
    @Transactional(readOnly = true)
    public User login(String loginname, String password){
        System.out.println("HrmService login -- >>");
        return userDao.getByLoginnameAndPassword(loginname,password);
    }

    /**     用户类       */
    /**
     * 获取用户
     * @param user user类
     * @param pageModel 分页模型
     * @return
     */
    @Transactional(readOnly = true)
    public List findUser(User user, PageModel pageModel){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        // 当前需要分页的总数据条数
        int count = userDao.count(params);

        if(count > 0){
            // 使用分页功能
            pageModel.setRecordCount(count);
            params.put("pageModel", pageModel);
        }
        List<User> users = userDao.findUser(params);
        return users;
    }

    public void deleteUserById(Integer id){
        userDao.delete(id);
    }
}
