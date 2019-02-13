package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Job;

import java.util.List;

public interface JobDao {
    // 根据 ID 获得 Job
    Job get(String id);
    // 获取所有 Job
    List<Job> getAllJob();
}
