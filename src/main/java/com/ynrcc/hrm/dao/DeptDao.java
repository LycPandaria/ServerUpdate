package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Dept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DeptDao {

    /**
     * 根据 ID 查找 Dept
     * @param id 要查找的部门的 ID
     * @return
     */
    Dept get(Integer id);

    /**
     * 查询查找所有的部门
     * @return
     */
    List<Dept> getAllDept();

    /**
     * 获取查询条数
     * @param params
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 查询部门信息
     * @param params
     * @return
     */
    List<Dept> findDept(Map<String, Object> params);

    /**
     * 更新部门信息
     * @param dept
     */
    void update(Dept dept);

    /**
     * 删除部门
     * @param id 要删除的部门的 ID
     */
    void delete(Integer id);

    /**
     * 新增用户
     * @param dept
     */
    void insert(Dept dept);
}
