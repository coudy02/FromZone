package com.yizhen.testoutofmemory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class OneActivity extends AppCompatActivity {

    private static Leak mLeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        mLeak = new Leak();

        ImageView iv_pic =findViewById(R.id.iv_pic);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic_1);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        iv_pic.setImageBitmap(bitmap);

    }

    class Leak{

    }

}
