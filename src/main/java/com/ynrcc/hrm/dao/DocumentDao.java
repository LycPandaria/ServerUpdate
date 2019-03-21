package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Document;

import java.util.List;
import java.util.Map;

public interface DocumentDao {

    /**
     * 根据 ID 删除文档
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 更新文档
     * @param record
     * @return
     */
    int save(Document record);

    /**
     * 根据 ID 查询文档信息
     * @param id
     * @return
     */
    Document get(Integer id);

    /**
     * 更新 文档信息
     * @param record
     * @return
     */
    int update(Document record);

    /**
     * 查询结果条数
     * @param params 模糊查询参数
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 查询文档信息
     * @param params 模糊查询参数和分页参数
     * @return
     */
    List<Document> findDocument(Map<String, Object> params);
}