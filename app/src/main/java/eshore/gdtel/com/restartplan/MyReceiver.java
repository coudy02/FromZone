package eshore.gdtel.com.restartplan;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    String Tag = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果是去电
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {

            Log.e(Tag, "去电======================================");
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
//            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        } else {
            // 查了下android文档，貌似没有专门用于接收来电的action,所以，非去电即来电.
            // 如果我们想要监听电话的拨打状况，需要这么几步 :
            Log.e(Tag, "来电======================================");

            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        }

    }

    // 设置一个监听器
    PhoneStateListener listener = new PhoneStateListener() {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // 注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.e(Tag, "挂断");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.e(Tag, "接听");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.e(Tag, "响铃:来电号码" + incomingNumber);
                    // 输出来电号码



                    break;
            }
        }
    };
}
