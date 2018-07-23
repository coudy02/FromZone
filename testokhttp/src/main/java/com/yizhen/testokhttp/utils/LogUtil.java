package com.yizhen.testokhttp.utils;

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
        if(tag != null){
            Log.i(tag, key+"="+value);
        } else {
            Log.i("zhenzhen", key+" = "+value);
        }
    }

}
