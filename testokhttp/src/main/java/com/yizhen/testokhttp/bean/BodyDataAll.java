package com.yizhen.testokhttp.bean;

/**
 * Created by Administrator on 2018/7/17.
 */

public class BodyDataAll {
    private String appId;
    private String method;
    private String tokenId;
    private BodyData data;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public BodyData getData() {
        return data;
    }

    public void setData(BodyData data) {
        this.data = data;
    }

    public BodyDataAll(String appId, String method, String tokenId, BodyData data) {
        this.appId = appId;
        this.method = method;
        this.tokenId = tokenId;
        this.data = data;
    }

}
