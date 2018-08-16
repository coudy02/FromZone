package eshore.gdtel.com.restartplan.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import eshore.gdtel.com.restartplan.Main3Activity;

public class MyFirstService extends Service {


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            startActivity(new Intent(MyFirstService.this, Main3Activity.class));
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // @IntDef(value = {Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY}, flag = true)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("zhenzhen", "开启服务");

        mHandler.sendEmptyMessageDelayed(100, 1000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.


        throw new UnsupportedOperationException("Not yet implemented");
    }
}
