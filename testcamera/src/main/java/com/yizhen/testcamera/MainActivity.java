package com.yizhen.testcamera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** 开始摄像 **/
                startService(new Intent(MainActivity.this, BackgroundVideoRecorder.class));

                /** 开始摄像 **/
//                startService(new Intent(MainActivity.this, VideoSerivice.class));
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Service", "stopService");
                /** * 启动定时器，到规定时间recordTime后执行停止录像任务 */
                Intent intents = new Intent();
                intents.setAction("com.example.testvideo.VideoService");
            }
        });
    }
}
