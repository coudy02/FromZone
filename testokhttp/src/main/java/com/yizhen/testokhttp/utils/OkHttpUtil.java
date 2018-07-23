package com.yizhen.testokhttp.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/7/18.
 */

public class OkHttpUtil {

    OkHttpClient mOkHttpClient = null;

    public OkHttpUtil(){

    }


    /**
     * 初始化OkhttpClient
     */
    private void initOkHttpClient() {
         mOkHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = mOkHttpClient.newBuilder();
        builder.cache(new Cache(new File("ai"), 20 * 1024));
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                // 保存cookie通常使用SharedPreferences
                LogUtil.setLog(null, "httpUrl_saveFromResponse=", httpUrl);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {

                // 从保存位置读取，注意此处不能为空，否则会导致空指针
                LogUtil.setLog(null, "httpUrl_loadForRequest=", httpUrl);

                return new ArrayList<>();
            }
        });
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                LogUtil.setLog(null, "chain_addInterceptor=", chain.toString());

                return null;
            }
        });
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                LogUtil.setLog(null, "chain_addNetworkInterceptor=", chain.toString());

                return null;
            }
        });
        builder.build();
    }


}
