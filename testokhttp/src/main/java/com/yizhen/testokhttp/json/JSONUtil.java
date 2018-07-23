package com.yizhen.testokhttp.json;

import android.support.annotation.Nullable;

import com.yizhen.testokhttp.utils.LogUtil;
import com.yizhen.testokhttp.bean.BodyData;
import com.yizhen.testokhttp.bean.BodyDataAll;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by Administrator on 2018/7/17.
 */

public class JSONUtil {

    public static String getJsonString(Object object) throws Exception {
        return JacksonMapper.getInstance().writeValueAsString(object);
    }

    public static Object toObject(String jsonString, Class cls) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, cls);
    }

    /**
     * 获取 请求 Json
     * 封装请求体内容，body
     * @param data
     * @return
     */
    @Nullable
    public static String getBodyJson(BodyData data) {
        BodyDataAll allCcs = new BodyDataAll("","","", data);
        String json = null;
        try {
            json = JSONUtil.getJsonString(allCcs);
//            LogUtil.setLog(null, "JSONUtil=", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
