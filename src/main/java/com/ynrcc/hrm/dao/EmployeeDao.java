package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeDao {
    /**
     * 根据 ID 查找
     * @param id 要查找的 Employee ID
     * @return
     */
    Employee get(Integer id);

    /**
     * 根据 ID 删除
     * @param id 要删除的 Employee ID
     * @return
     */
    int delete(Integer id);

    /**
     * 新增 Employee
     * @param record
     * @return
     */
    int save(Employee record);

    /**
     * 更新 Employee
     * @param record
     * @return
     */
    int update(Employee record);

    /**
     * 查询 Employee 信息
     * @param params 模糊查询参数以及分页参数
     * @return
     */
    List<Employee> findEmployee(Map<String, Object> params);

    /**
     * 获取查询结果条数
     * @param params 模糊查询参数
     * @return8u
     */
    int count(Map<String, Object> params);
}
