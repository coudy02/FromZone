package com.yizhen.testokhttp.json;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by Administrator on 2018/7/17.
 */

public class JacksonMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JacksonMapper() {
    }

    public static ObjectMapper getInstance() {
        return mapper;
    }

}
