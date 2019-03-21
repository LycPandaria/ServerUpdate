package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {
    /**
     * 根据用户名密码查询用户
     * @param loginname
     * @param password
     * @return
     */
    User getByLoginnameAndPassword(
            @Param("loginname") String loginname,
            @Param("password") String password
    );

    /**
     * 根据 ID 查询用户
     * @param id
     * @return
     */
    User get(Integer id);

    /**
     * 获取 SQL 数据条数
     * @param params
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 用户查询，查询条件放置于 params的 user 中
     * @param params
     * @return
     */
    List<User> findUser(Map<String, Object> params);

    /**
     * 根据 ID 删除用户
     * @param id
     * @return
     */
    void delete(Integer id);

    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    /**
     * 插入新用户
     * @param user
     */
    void insert(User user);
}
