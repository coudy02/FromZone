package eshore.gdtel.com.restartplan.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Administrator on 2018/6/25.
 */

public class LooperThread extends Thread {

    private Handler mHandler;

    public void run(){
        Looper.prepare();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
                // 定义消息处理逻辑

            }
        };
        Looper.loop();
    }


}
