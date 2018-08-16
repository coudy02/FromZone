package eshore.gdtel.com.restartplan;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class MainActivity extends Activity {

    private static final String TAG = "Kintai";
    private MySurfaceView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题


        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        Log.d(TAG, "initUI...");
        mView = (MySurfaceView) findViewById(R.id.mView);
    }

}