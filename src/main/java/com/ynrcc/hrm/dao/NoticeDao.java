package com.ynrcc.hrm.dao;

import com.ynrcc.hrm.model.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeDao {
    /**
     * 根据 ID 查找 公告
     * @param id 公告 ID
     * @return
     */
    Notice get(Integer id);

    /**
     * 查找 Notice
     * @param params
     * @return
     */
    List<Notice> findNotice(Map<String,Object> params);

    /**
     * 获取数据条数已供分页
     * @param params
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 更新
     * @param notice
     */
    void update(Notice notice);

    /**
     * 根据 ID 删除 Notice
     * @param id 要删除的 Notice 的 ID
     */
    void delete(Integer id);

    /**
     * 新增 Notice
     * @param notice
     */
    void save(Notice notice);
}
