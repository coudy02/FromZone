package com.yizhen.testokhttp.bean;

/**
 * Created by Administrator on 2018/7/20.
 */

public class BodyData_version extends BodyData {


    private String versionType;

    public BodyData_version(String versionType) {
        this.versionType = versionType;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }
}
