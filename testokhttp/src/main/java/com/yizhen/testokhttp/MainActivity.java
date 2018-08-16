package com.yizhen.testokhttp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yizhen.testokhttp.bean.BodyData_ccs;
import com.yizhen.testokhttp.bean.BodyData_init;
import com.yizhen.testokhttp.bean.BodyData_version;
import com.yizhen.testokhttp.json.JSONUtil;
import com.yizhen.testokhttp.utils.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final int SUCCESS_PIC = 1001;

    private final int FAIL_PIC = 1002;

    //
    OkHttpClient mOkHttpClient;

    private TextView tv_content;
    private ImageView img_downPic;
    private Button btn_toDownLoad;

    Executor executors = null;

    private String MJson ;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_content = findViewById(R.id.tv_content);
        img_downPic = findViewById(R.id.img_downPic);
        btn_toDownLoad = findViewById(R.id.btn_toDownLoad);
        btn_toDownLoad.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        executors = Executors.newSingleThreadExecutor();
        initOkHttpClient();
//        method_get();
//        method_post();
//        method_downPic();
        method_upLoadFile();
//        method_post_SSL();
//        method_post_SSL_2();

//        method_apk();
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1000:{
                    if(MJson!=null){
                        tv_content.setText(MJson);
                    }
                    break;
                }
                case SUCCESS_PIC:{
                    byte[] byte_pic = (byte[]) msg.obj;
                    Bitmap bm = BitmapFactory.decodeByteArray(byte_pic, 0, byte_pic.length);
                    img_downPic.setImageBitmap(bm);
                    break;
                }
                case FAIL_PIC:{

                    break;
                }
                case 1008:{
                    progressBar.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };

    //bussIntfAction!getVersionUpdateInfo.action

    /**
     * post请求的 测试
     *
     */
    private void method_post_SSL_2() {
        String url = "https://mycmstest.cdispatch.pw:8012/dp-bussintf/bussIntfAction!getVersionUpdateInfo.action";
        // 创建 请求body
        BodyData_version bodyData_version = new BodyData_version("0");
        String json = JSONUtil.getBodyJson(bodyData_version);
        LogUtil.setLog(null, "json_init=", json);
        if(json != null){
            RequestBody body = RequestBody.create(JSON, json);
            // 创建request，
            Request request = new Request.Builder().post(body).url(url).build();
            LogUtil.setLog(null, "request=", request);
            // 同步请求，得到响应
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.setLog(null, "post_fail=", "post请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    LogUtil.setLog(null, "post_json=", json);
                    MJson = json;
                    mHandler.sendEmptyMessage(1000);
                }
            });
        }
    }
    /**
     * post请求的 测试
     *
     */
    private void method_post_SSL() {
        String url = "https://mycmstest.cdispatch.pw:8012/dp-bussintf/bussIntfAction!initParamObject.action";
        // 创建 请求body
        BodyData_init bodyData_init = new BodyData_init();
        bodyData_init.setUserCode("zyzmb01");
        bodyData_init.setUserPwd("defe12aad396f90e6b179c239de260d4");
        bodyData_init.setImeiCode("865056029428097");
        bodyData_init.setUserType("MOB");
        bodyData_init.setCustId("1");
        bodyData_init.setVersionType("1");
        bodyData_init.setInitType("1");
        bodyData_init.setProject("COMMON");
        String json = JSONUtil.getBodyJson(bodyData_init);
        LogUtil.setLog(null, "json_init=", json);
        if(json != null){
            RequestBody body = RequestBody.create(JSON, json);
            // 创建request，
            Request request = new Request.Builder().post(body).url(url).build();
            LogUtil.setLog(null, "request=", request);
            // 同步请求，得到响应
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.setLog(null, "post_fail=", "post请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    LogUtil.setLog(null, "post_json=", json);
                    MJson = json;
                    mHandler.sendEmptyMessage(1000);
                }
            });
        }
    }

    /**
     * 上传文件到服务器
     */
    public static final String TYPE = "application/octet-stream";
    private void method_upLoadFile(){

        File file = new File(Environment.getExternalStorageDirectory(), "wangshu.jpg");

        if(!file.exists()){
            Toast.makeText(this, "没有此文件", Toast.LENGTH_SHORT).show();;
            return;
        }
        RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Length", file.length()+"");
        headerMap.put("filename", file.getName());
        headerMap.put("fileType", 3+"");
        headerMap.put("svcType", "SVC10001");
        headerMap.put("savePathType","2");
        headerMap.put("smallFlag", "0");

        Headers headers = Headers.of(headerMap);

        Request request = new Request.Builder()
                .headers(headers)
//                .addHeader("Content-Length", file.length()+"")
//                .addHeader("filename", file.getName())
//                .addHeader("fileType", 3+"")
//                .addHeader("svcType", "SVC10001")
//                .addHeader("savePathType","2")
//                .addHeader("smallFlag", "0")
                .post(fileBody)
                .url("http://10.19.155.212:8001/uploadFile")
                .build();
        LogUtil.setLog(null, "request", request.toString());
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.setLog(null, "上传返回", "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.setLog(null, "上传返回", response.toString());
            }
        });

    }

    // http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk
    /**
     * get 方法 apk包
     */
    private void method_apk() {

        String picUrl = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";

        Request request = new Request.Builder().url(picUrl).build();
        LogUtil.setLog(null, "pic_request=", request.toString());
        progressBar.setVisibility(View.VISIBLE);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {



                byte[] apkByte = response.body().bytes();
                LogUtil.setLog(null, "apkByte=", apkByte.length);
//                Message msg = new Message();
//                msg.what = SUCCESS_PIC;
//                msg.obj = apkByte;
//                mHandler.sendMessage(msg);



                InputStream is = new ByteArrayInputStream(apkByte);
                File file = new File(Environment.getExternalStorageDirectory(), "douban.apk");
//                if(file.exists()){
//                    file.delete();
//                } else {
//                    file = new File(Environment.getExternalStorageDirectory(), "douban.apk");
//                }
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                while(is.read(bytes) > 0){
                    fos.write(bytes, 0, bytes.length);
                }
                is.close();
                fos.close();

                mHandler.sendEmptyMessage(1008);
                // 安装
                Intent apkintent = new Intent(Intent.ACTION_VIEW);
                apkintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                LogUtil.setLog(null, "file", file.getAbsolutePath());
                Uri puri = Uri.fromFile(file);
                apkintent.setDataAndType(puri,
                        "application/vnd.android.package-archive");
                startActivity(apkintent);
            }
        });
    }
//
//    //打开APK程序代码
//
//    public void openFile(File file, Context var1) {
//        Intent var2 = new Intent();
//        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        var2.setAction(Intent.ACTION_VIEW);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
//            Uri uriForFile = FileProvider.getUriForFile(MainActivity.this, "com.kaiguangshanpin.FileProvider", file);
//            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            var2.setDataAndType(uriForFile,  "application/vnd.android.package-archive");
//        }else{
////            var2.setDataAndType(Uri.fromFile(file), getMIMEType(file));
//        }
//        try {
//            var1.startActivity(var2);
//        } catch (Exception var5) {
//            var5.printStackTrace();
//            Toast.makeText(var1, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
//        }
//    }


    /**
     * get 方法 下载一张图片你
     */
    private void method_downPic() {

        String picUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531840157464&di=33de0a8ba8b547d557e2ac0424e970c1&imgtype=0&src=http%3A%2F%2Fpic32.photophoto.cn%2F20140709%2F0021033874761617_b.jpg";

        Request request = new Request.Builder().url(picUrl).build();
        LogUtil.setLog(null, "pic_request=", request.toString());
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] picByte = response.body().bytes();
                Message msg = new Message();
                msg.what = SUCCESS_PIC;
                msg.obj = picByte;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * post请求的 测试
     *
     */
    private void method_post() {
//        String url = "http://10.19.155.230:8001/dp-bussintf/bussIntfAction!getCcsServerInfo.action";
        String url = "https://mycmstest.cdispatch.pw:8012/dp-bussintf/bussIntfAction!getCcsServerInfo.action";
        // 创建 请求body
        BodyData_ccs dataCcs = new BodyData_ccs("1");
        String json = JSONUtil.getBodyJson(dataCcs);
        LogUtil.setLog(null, "json_ccs=", json);
        if(json != null){
            RequestBody body = RequestBody.create(JSON, json);
            // 创建request，
            Request request = new Request.Builder().post(body).url(url).build();
            LogUtil.setLog(null, "request=", request);
            // 同步请求，得到响应
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.setLog(null, "post_fail=", "post请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    LogUtil.setLog(null, "post_json=", json);
                    MJson = json;
                    mHandler.sendEmptyMessage(1000);
                }
            });
        }
    }

    /**
     * get请求的 测试
     */
    private void method_get() {
        String url = "https://www.baidu.com/";
        // 创建request，
        Request request = new Request.Builder().url(url).build();
        // 同步请求，得到响应
        Response response = null;
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.setLog(null, "Get_fail=", "get请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    LogUtil.setLog(null, "Get_json=", json);
                    MJson = json;
                    mHandler.sendEmptyMessage(1000);
                }
            });
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_toDownLoad:{

                Intent intent  = new Intent(this, ListPhotoActivity.class);
                startActivity(intent);
                this.finish();

                break;
            }
        }
    }
}
