package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    // 根据用户名密码查找用户
    User getByLoginnameAndPassword(
            @Param("loginname") String loginname,
            @Param("password") String password
    );

    // 根据 ID 查找用户
    User get(Integer id);
}
