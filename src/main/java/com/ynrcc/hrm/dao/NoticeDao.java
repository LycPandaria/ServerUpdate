package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Notice;

public interface NoticeDao {
    // 根据 ID 查询 Notice
    Notice get(String id);
}
