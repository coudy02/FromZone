package com.yizhen.testselectphoto.utils;

import android.util.Log;

/**
 * Created by Administrator on 2018/7/13.
 */

public class LogUtil {

    /**
     * 打印Log日志
     * @param tag
     * @param key
     * @param value
     */
    public static void setLog(String tag, String key, Object value){
        if(tag!=null){
            Log.e(tag, key+"="+value);
        } else {
            Log.e("zhenzhen", key+" = "+value);
        }
    }

}
