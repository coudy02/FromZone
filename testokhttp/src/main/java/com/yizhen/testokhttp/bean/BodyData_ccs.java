package com.yizhen.testokhttp.bean;

/**
 * Created by Administrator on 2018/7/17.
 */

public class BodyData_ccs extends BodyData {
    public BodyData_ccs(){}
    private String custId;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public BodyData_ccs(String custId) {
        this.custId = custId;
    }

}
