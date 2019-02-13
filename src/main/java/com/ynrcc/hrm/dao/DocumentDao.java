package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Document;

public interface DocumentDao {
    // 根据 ID 查询 Document
    Document get(String id);
}
