package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Job;

import java.util.List;
import java.util.Map;

public interface JobDao {
    /**
     * 根据 ID 查询
     * @param id Job ID
     * @return
     */
    Job get(Integer id);

    /**
     * 获取所有的 Job
     * @return
     */
    List<Job> getAllJob();

    /**
     * 获取查询条数
     * @param params 模糊查询参数
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 查询 Job 信息
     * @param params 模糊查询参数
     * @return
     */
    List<Job> findJob(Map<String, Object> params);

    /**
     * 更新 Job
     * @param job
     */
    void update(Job job);

    /**
     * 新增 Job
     * @param job
     */
    void save(Job job);

    /**
     * 根据 ID 删除 Job
     * @param id 要删除的 Job ID
     */
    void delete(Integer id);
}
