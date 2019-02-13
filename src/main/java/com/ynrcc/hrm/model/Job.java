package com.ynrcc.hrm.model;

import java.io.Serializable;

public class Job implements Serializable {
    private Integer id;     // id
    private String name;    // 职位名称
    private String remark;  // 备注

    public Job() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
