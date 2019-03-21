package com.ynrcc.hrm.service;

import com.ynrcc.hrm.dao.*;
import com.ynrcc.hrm.model.*;
import com.ynrcc.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    /** ----------------------------    用户类   --------------------------------------------------    */
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

    /**
     * 根据用户 ID 删除
     * @param id 要删除的用户的 ID
     */
    public void deleteUserById(Integer id){
        userDao.delete(id);
    }

    /**
     * 根据 ID 查询用户
     * @param id 要查找的用户的 ID
     * @return
     */
    public User selectUserById(Integer id){
       return userDao.get(id);
    }

    /**
     * 更新用户
     * @param user
     */
    public void updateUser(User user){
        userDao.update(user);
    }

    /**
     * 新增用户
     * @param user
     */
    public void insertUser(User user){
        user.setCreateDate(new Date());
        userDao.insert(user);
    }

    /** ----------------------------    部门类   --------------------------------------------------    */

    @Transactional(readOnly=true)
    public List<Dept> findAllDept() {

        return deptDao.getAllDept();
    }

    /**
     * 查询部门信息
     * @param dept 模糊查询参数
     * @param pageModel
     * @return
     */
    public List<Dept> findDept(Dept dept, PageModel pageModel){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dept", dept);
        // 获取查询结果条数
        int count = deptDao.count(params);
        if(count > 0) {
            pageModel.setRecordCount(count);
            params.put("pageModel", pageModel);
        }
        return deptDao.findDept(params);
    }


    /**
     * 删除部门
     * @param id 要删除的部门 ID
     */
    public void deleteDeptById(Integer id) {
        deptDao.delete(id);

    }


    /**
     * 新增部门
     * @param dept
     */
    public void addDept(Dept dept) {
        deptDao.insert(dept);

    }


    /**
     * 根据 ID 查找部门信息
     * @param id
     * @return
     */
    public Dept findDeptById(Integer id) {

        return deptDao.get(id);
    }

    /**
     * x更新部门信息
     * @param dept 要更新的部门信息
     */
    public void modifyDept(Dept dept) {
        deptDao.update(dept);
    }


    /** ---------------------------------- 职位 ------------------------------------- */

    /**
     * 查询所有的 Job
     * @return
     */
    @Transactional(readOnly=true)
    public List<Job> findAllJob() {
        return jobDao.getAllJob();
    }


    /**
     * 查询 Job
     * @param job 模糊查询参数
     * @param pageModel 分页模型
     * @return
     */
    @Transactional(readOnly=true)
    public List<Job> findJob(Job job, PageModel pageModel) {
        /** 当前需要分页的总数据条数  */
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("job", job);
        int recordCount = jobDao.count(params);
        pageModel.setRecordCount(recordCount);

        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }

        List<Job> jobs = jobDao.findJob(params);

        return jobs;
    }

    /**
     * 根据 Job ID 删除 Job
     * @param id
     */
    public void removeJobById(Integer id) {
        jobDao.delete(id);

    }

    /**
     * 新增 Job
     * @param job
     */
    public void addJob(Job job) {
        jobDao.save(job);

    }

    /**
     * 根据 ID 查找 Job
     * @param id
     * @return
     */
    @Transactional(readOnly=true)
    public Job findJobById(Integer id) {
        return jobDao.get(id);
    }

    /**
     * 更新 Job
     * @param job
     */
    public void modifyJob(Job job) {
        jobDao.update(job);

    }


    /**--------------------------------- 公告 ---------------------------------**/
    /**
     * 查找 Notice
     * @param notice 模糊查询参数
     * @param pageModel 分页模型
     * @return
     */
    @Transactional(readOnly=true)
    public List<Notice> findNotice(Notice notice, PageModel pageModel) {
        /** 当前需要分页的总数据条数  */
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("notice", notice);
        int recordCount = noticeDao.count(params);
        pageModel.setRecordCount(recordCount);

        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }

        List<Notice> notices = noticeDao.findNotice(params);

        return notices;
    }

    /**
     * 根据 ID 查找 Notice
     * @param id
     * @return
     */
    @Transactional(readOnly=true)
    public Notice findNoticeById(Integer id) {
        return noticeDao.get(id);
    }

    /**
     * 根据 ID 删除 Notice
     * @param id
     */
    public void removeNoticeById(Integer id) {
        noticeDao.delete(id);
    }

    /**
     * 新增 Notice
     * @param notice
     */
    public void addNotice(Notice notice) {
        noticeDao.save(notice);

    }

    /**
     * 更新 Notice
     * @param notice
     */
    public void modifyNotice(Notice notice) {
        noticeDao.update(notice);

    }

    /** ---------------------- 外包人员 -----------------*/
    /**
     * 查询 Employee 信息
     * @param employee 模糊查询参数
     * @param pageModel
     * @return
     */
    @Transactional(readOnly=true)
    public List<Employee> findEmployee(Employee employee,PageModel pageModel) {
        /** 当前需要分页的总数据条数  */
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("employee", employee);

        int recordCount = employeeDao.count(params);
        pageModel.setRecordCount(recordCount);

        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }
        List<Employee> employees = employeeDao.findEmployee(params);
        return employees;
    }

    /**
     * 根据 ID 删除 Employee
     * @param id
     */
    public void removeEmployeeById(Integer id) {
        employeeDao.delete(id);

    }

    /**
     * 根据 ID 查询 Employee 信息
     * @param id
     * @return
     */
    @Transactional(readOnly=true)
    public Employee findEmployeeById(Integer id) {

        return employeeDao.get(id);
    }


    /**
     * 新增 Employee
     * @param employee
     */
    public void addEmployee(Employee employee) {
        employeeDao.save(employee);

    }


    /**
     * 更新 Employee 信息
     * @param employee
     */
    public void modifyEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    /** ----------------------- 文档 -----------------------*/
    /**
     * 查询文档信息
     * @param document 模糊查询参数
     * @param pageModel 分页模型
     * @return
     */
    @Transactional(readOnly=true)
    public List<Document> findDocument(Document document, PageModel pageModel) {
        /** 当前需要分页的总数据条数  */
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("document", document);
        int recordCount = documentDao.count(params);
        pageModel.setRecordCount(recordCount);

        if(recordCount > 0){
            /** 开始分页查询数据：查询第几页的数据 */
            params.put("pageModel", pageModel);
        }

        List<Document> documents = documentDao.findDocument(params);

        return documents;
    }


    /**
     * 新增文档
     * @param document
     */
    public void addDocument(Document document) {
        documentDao.save(document);

    }

    /**
     * 删除文档
     * @param id
     */
    public void removeDocumentById(Integer id) {
        documentDao.delete(id);

    }

    /**
     * 更新文档
     * @param document
     */
    public void modifyDocument(Document document) {
        documentDao.update(document);

    }

    /**
     * 根据 ID 查找文档
     * @param id 要查找的文档的 ID
     * @return
     */
    @Transactional(readOnly=true)
    public Document findDocumentById(Integer id) {

        return documentDao.get(id);
    }



}
