package eshore.gdtel.com.restartplan;


import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class Main2Activity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;//预览摄像头
    private SurfaceHolder surfaceHolder;
    private Button button;//拍照按钮
    private Camera camera;
    private Camera.AutoFocusCallback myAutoFocusCallback1 = null;//只对焦不拍照
    public static final int only_auto_focus = 110;
    int issuccessfocus = 0;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case only_auto_focus:
                    if (camera != null)
                        camera.autoFocus(myAutoFocusCallback1);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        applyCameraPermission();
        initView();
        initData();
        initListener();
    }

    private int CAMERA_REQUREST =0;

    private void applyCameraPermission(){//申请摄像头权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//已经授权

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUREST);//1 can be another integer
        }
    }

    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.main_surface_view);
        button = (Button) findViewById(R.id.main_button);
    }


    private void initData() {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        myAutoFocusCallback1 = new Camera.AutoFocusCallback() {

            public void onAutoFocus(boolean success, Camera camera) {
                // TODO Auto-generated method stub
                if (success)//success表示对焦成功
                {
                    issuccessfocus++;
                    if (issuccessfocus <= 1)
                        mHandler.sendEmptyMessage(only_auto_focus);
                    Log.i("qtt", "myAutoFocusCallback1: success..." + issuccessfocus);
                } else {
                    //if (issuccessfocus == 0) {
                    mHandler.sendEmptyMessage(only_auto_focus);
                    //}
                    Log.i("qtt", "myAutoFocusCallback1: 失败...");
                }
            }
        };
    }

    private void initListener() {
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null) {
                    if (camera != null)
                        camera.autoFocus(myAutoFocusCallback1);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main2Activity.this, "button", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initCamera() {
        Log.e("zhen", "camera="+camera);
        Camera.Parameters parameters = camera.getParameters();//获取camera的parameter实例
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
        Camera.Size optionSize = getOptimalPreviewSize(sizeList, surfaceView.getWidth(), surfaceView.getHeight());//获取一个最为适配的屏幕尺寸
        parameters.setPreviewSize(optionSize.width, optionSize.height);//把只存设置给parameters
        camera.setParameters(parameters);//把parameters设置给camera上
        camera.startPreview();//开始预览
        camera.setDisplayOrientation(90);//将预览旋转90度

        camera.setPreviewCallback(callBack);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
//            ReleaseCamera();
            camera = Camera.open();
            camera.setPreviewDisplay(surfaceHolder);

        } catch (Exception e) {
            if (null != camera) {
                camera.release();
                camera = null;
            }
            e.printStackTrace();
            Toast.makeText(Main2Activity.this, "启动摄像头失败,请开启摄像头权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void ReleaseCamera()
    {
        if(camera != null)
        {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initCamera();

    }

    Camera.PreviewCallback callBack = new Camera.PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Log.e("zhen", data.length +"--------");
        }
    };

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != camera) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
}
