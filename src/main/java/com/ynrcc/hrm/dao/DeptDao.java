package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Dept;

import java.util.List;

public interface DeptDao {
    // 根据 ID 查找 Dept
    Dept get(String id);
    // 所有的 Dept
    List<Dept> getAllDept();
}
