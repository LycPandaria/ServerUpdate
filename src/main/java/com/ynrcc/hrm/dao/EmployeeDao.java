package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Employee;

public interface EmployeeDao {
    // 根据 ID 查找 Employee
    Employee get(String id);
}
