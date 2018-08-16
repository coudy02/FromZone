package com.yizhen.testselectphoto.bean;

/**
 * Created by Administrator on 2018/7/12.
 */

/**
 * 单张图片信息
 */
public class PhotoBean {
    public String pName;
    public String pPath;
    public boolean isSelected = false; // 是否被选中，true 被选，false 没选，默认都没选
    public long pSize = 0;
}
