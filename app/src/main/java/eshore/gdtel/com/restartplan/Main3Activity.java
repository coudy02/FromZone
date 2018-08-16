package eshore.gdtel.com.restartplan;

import android.Manifest;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import eshore.gdtel.com.restartplan.service.MyFirstService;

import static android.os.Looper.myLooper;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener{


    private int REQUREST =0;

    private Thread mUIThread;

    private Button btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {//已经授权

        } else { // 否则需要动态授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUREST);//1 can be another integer
        }

        btn_click = (Button)findViewById(R.id.btn_click);
        btn_click.setOnClickListener(this);


        //本地App 的ip


        startService(new Intent(this, MyFirstService.class));

        Button but = (Button)findViewById(R.id.get_battery);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //要特别的注意这一句代码。注册一个电池信息服务
               registerReceiver(new BatteryReceiver(),new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

           /*也可以在这里获取，通过batteryIntent .***
           *例如：”batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);//当前电量
           */
            }
        });

        BatteryManager mBM = (BatteryManager) this.getSystemService(Context.BATTERY_SERVICE);


//        BatteryStatsHelper mStatsHelper = new BatteryStatsHelper(this, true);//实例化

    }






    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e("zhenzhen","开始Main3Activity");

    }

    private void uiThread() {
        mUIThread = Thread.currentThread();
        Log.e("zhenzhen", "mUIThread="+mUIThread);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.e("zhenzhen", "step 1 "+Thread.currentThread().getName());

                Handler handler=new Handler(getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Do Ui method
                        Log.e("qdx", "step 2 "+Thread.currentThread().getName());
                    }
                });

            }
        }).start();
    }


    private static Looper sMainLooper;  // guarded by Looper.class

    public static void prepareMainLooper() {
        sMainLooper.prepare();
        synchronized (Looper.class) {
            if (sMainLooper != null) {
                throw new IllegalStateException("The main Looper has already been prepared.");
            }
            sMainLooper = myLooper();
        }
    }


    Handler mHandler = new Handler();

    public void runOnUIThread(Runnable action){
        Log.e("zhenzhen", "entThread="+Thread.currentThread() );
        if(Thread.currentThread() != mUIThread){  // 如何判断一个线程是子线程还是UI线程
            mHandler.post(action);
        } else {
            action.run();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View v) {
//        runOnUIThread(new Runnable(){
//
//            @Override
//            public void run() {
//                Toast.makeText(Main3Activity.this, "hello", Toast.LENGTH_SHORT).show();
//            }
//        });

        new Thread(new Runnable(){
            @Override
            public void run() {

                Log.e("zhenzhen", "entThread="+Thread.currentThread() );

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Main3Activity.this, "hello", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();

    }
}


