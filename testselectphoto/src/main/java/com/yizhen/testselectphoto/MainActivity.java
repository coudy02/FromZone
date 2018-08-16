package com.yizhen.testselectphoto;

import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.yizhen.testselectphoto.adapter.PhotoDirAdapter;
import com.yizhen.testselectphoto.bean.PhotoBean;
import com.yizhen.testselectphoto.bean.PhotoDirBean;
import com.yizhen.testselectphoto.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<PhotoBean> photoBeanList = null;
    /**存放文件夹信息*/
    private ArrayList<PhotoDirBean> photoDirList = null;
    /** 为了避免重复检测 */
    private HashSet<String> dirSet = null;

    private Button btn_dir;

    private PopupWindow pw;

    private RecyclerView rc_photo;

    Executor executor = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** 声明数组 */
        photoBeanList = new ArrayList<PhotoBean>();
        photoDirList = new ArrayList<PhotoDirBean>();
        dirSet = new HashSet<String>();

        // 查询手机图片
        thread.start();

        // 声明控件
        btn_dir = findViewById(R.id.btn_dir);
        btn_dir.setOnClickListener(this);
        rc_photo = findViewById(R.id.rc_photo);

        pw = new PopupWindow();

        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_dir:{

                if(pw.isShowing()){
                    pw.dismiss();
                } else {
                    setPopWindowMsg();
                }
                break;
            }
        }
    }

    private void setPopWindowMsg(){
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow_dir, null);
        RecyclerView rv = view.findViewById(R.id.rc_dir_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        PhotoDirAdapter dirAdapter = new PhotoDirAdapter(photoDirList);
        rv.setAdapter(dirAdapter);
//        pw = new PopupWindow(view, 100, 100);
        pw.setContentView(view);
        pw.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
        pw.setHeight(RecyclerView.LayoutParams.WRAP_CONTENT);
        pw.showAsDropDown(btn_dir);
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            getPicPath();
        }
    });

    // 获取手机里所有图片路径， 查询是耗时操作，需要放到线程里执行。
    private void getPicPath() {
        // 获取图片uri
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Log.e("zhenzhen", "uri="+uri); // 是想看看这个uir是什么  uri=content://media/external/images/media
        // uri：需要检索的内容；projection 想要查找哪几列，null是指检索全部，低效；
        // selection: 是一个过滤器，声明要返回的行，null返回uri给定的所有行（有多少数据就返回多少） ；
        // selectionArgs: 这个参数的值，与sql语句中 "?" 对应，值绑定符号 ; sortOrder: 排序，null按默认
        Cursor cursor = getContentResolver().query(uri, null,null, null,null);
        if(cursor != null){
            LogUtil.setLog(null,"cursorSize", cursor.getCount());
        }
        // 获取图片文件信息
        while (cursor.moveToNext()){
            PhotoBean photoBean = new PhotoBean();
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            Long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
            // 获取该图片的文件夹名
            File parentFile = new File(path).getParentFile();
            if (parentFile == null)
                continue;
            //获取到文件地址
            String dirPath = parentFile.getAbsolutePath();
            photoBean.pName = name;
            photoBean.pPath = path;
            photoBean.pSize = size;
            photoBeanList.add(photoBean);
            if(dirSet.contains(dirPath)){
                continue;
            } else {
                dirSet.add(dirPath);
                PhotoDirBean pdir = new PhotoDirBean();
                pdir.pdPath = dirPath;
                pdir.pdName = dirPath.substring(dirPath.lastIndexOf("/"));
                photoDirList.add(pdir);
            }
        }
        LogUtil.setLog(null,"photoBeanList.size", photoBeanList.size());
        LogUtil.setLog(null,"photoDirList.size", photoDirList.size());

//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                PhotoDirAdapter dirAdapter = new PhotoDirAdapter(photoDirList);
//                rc_photo.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                rc_photo.setAdapter(dirAdapter);
//            }
//        });
    }

    //        mHandler.sendEmptyMessage(100);
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:{
                    PhotoDirAdapter dirAdapter = new PhotoDirAdapter(photoDirList);
                    rc_photo.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rc_photo.setAdapter(dirAdapter);
                    break;
                }
            }
        }
    };

}
