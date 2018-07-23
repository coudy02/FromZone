package com.lypeer.testcontextprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Uri uri = Uri.parse("content://hb.android.contentProvider/teacher");

    private TextView tv_content;
    private SharedPreferences sp;
    //此为提供方的包名
//        private static String PREFERENCE_PACKAGE="com.yizhen.testsharepreference";
    private static String PREFERENCE_PACKAGE="com.gdtel.eshore.check";
    public static int MODE = Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE;

    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 跨 应用 获取 contextProvider 的信息 */
//        getContextProviderMsg();
        /* 跨进程（app）获取 sharePreference 的数据 */
        getSharePreferenceMsg();

        }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageAtTime(100, 100);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 100:{
                    initSharePreference();
                    getSharePreferenceMsg();
                    mHandler.sendEmptyMessageAtTime(100, 100);
                    break;
                }
            }
        }
    };

    int i = 0;

    /**
     * 初始化 sharePreference
     */
    private void initSharePreference(){
        if(isAvilible(this, PREFERENCE_PACKAGE)) {
            try {
//                c = this.createPackageContext(PREFERENCE_PACKAGE, Context.CONTEXT_IGNORE_SECURITY);
//                sp = c.getSharedPreferences("GPS_info", MODE);
                sp = this.getSharedPreferences("GPS_info", MODE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("longitude", i + "");
                ed.putString("latitude","0");
                ed.commit();
                i ++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "没有安装该app", Toast.LENGTH_SHORT).show();
        }
    }

//            sp=c.getSharedPreferences("mySp",MODE);
//            tv_content= (TextView) findViewById(R.id.tv_content);
//            String content=sp.getString("content","");
//            tv_content.setText(content);

    /**
     * 跨进程（app）获取 sharePreference 的数据
     */
    private void getSharePreferenceMsg(){
        try {
            //判断  PREFERENCE_PACKAGE 是否存在
            if(isAvilible(this, PREFERENCE_PACKAGE)){

                tv_content= (TextView) findViewById(R.id.tv_content);
                String longitude=sp.getString("longitude","0");
                String latitude=sp.getString("latitude","0");
                tv_content.setText(longitude + " - " + latitude);
                Log.e("zhen", longitude + " - " + latitude);

            } else {
                Toast.makeText(this, "没有安装该app", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跨 应用 获取 contextProvider 的信息
     */
    private void getContextProviderMsg(){
        ContentResolver cr = getContentResolver();
        // 查找id为1的数据
        Log.e("zhenzhen", cr+"--");
        Cursor c = cr.query(uri, null, "_ID=?", new String[] { "1" }, null);
        //这里必须要调用 c.moveToFirst将游标移动到第一条数据,不然会出现index -1 requested , with a size of 1错误；cr.query返回的是一个结果集。
        Log.e("zhenzhen", c+"--");
        if (c.moveToFirst() == false) {
            // 为空的Cursor
            return;
        }
        int name = c.getColumnIndex("name");
        System.out.println(c.getString(name));
        c.close();

        Toast.makeText(this, name+"  hello", Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断相对应的APP是否存在
     *
     * @param context
     * @param packageName(包名)(若想判断QQ，则改为com.tencent.mobileqq，若想判断微信，则改为com.tencent.mm)
     * @return
     */
    public boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        //获取手机系统的所有APP包名，然后进行一一比较
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName
                    .equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

}
