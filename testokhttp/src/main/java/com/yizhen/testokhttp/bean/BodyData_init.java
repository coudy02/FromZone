package com.yizhen.testokhttp.bean;

/**
 * {
 "appId":"",
 "method":"",
 "tokenId":"",
 "data":{
 "userCode":"test000004",                          --用户编码
 "userPwd":"e10adc3949ba59abbe56e057f20f883e",     --用户密码
 "imeiCode":"123",                                 --IMEI终端编码
 "userType":"CONSOLE",                             --用户类型：MOB:手机终端  CONSOLE:控制台
 "custId":"2",                                     --单位编号
 "versionType":"1" ,                               --版本类型：[0:PC,1:android,2:IOS]
 "initType":"1",                                   --初始类型：1：需登录（返回登录、客户管理员、版本信息）；2：无需登录（返回版本信息）
 "project" : "FSYUN"                               --系统标识： FSYUN：佛山云平台    GZDS:广州地税    ZSZW:中山政务   FSTJ:佛山同济   其他：通用
 }
 }
 */

public class BodyData_init extends BodyData {

    private String userCode; //":"test000004",                          --用户编码
    private String userPwd; //":"e10adc3949ba59abbe56e057f20f883e",     --用户密码
    private String imeiCode; //":"123",                                 --IMEI终端编码
    private String userType; //":"CONSOLE",                             --用户类型：MOB:手机终端  CONSOLE:控制台
    private String custId; //":"2",                                     --单位编号
    private String versionType; //":"1" ,                               --版本类型：[0:PC,1:android,2:IOS]
    private String initType; //":"1",                                   --初始类型：1：需登录（返回登录、客户管理员、版本信息）；2：无需登录（返回版本信息）
    private String project; //" : "FSYUN"

    public BodyData_init() {

//        BodyData_init bodyData_init = new BodyData_init();
//        bodyData_init.setUserCode();
//        bodyData_init.setUserPwd();
//        bodyData_init.setImeiCode();
//        bodyData_init.setUserType();
//        bodyData_init.setCustId();
//        bodyData_init.setVersionType();
//        bodyData_init.setInitType();
//        bodyData_init.setProject();

    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getImeiCode() {
        return imeiCode;
    }

    public void setImeiCode(String imeiCode) {
        this.imeiCode = imeiCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public String getInitType() {
        return initType;
    }

    public void setInitType(String initType) {
        this.initType = initType;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
