package com.yizhen.testcamera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.Toast;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    /**
     * 不增加：mCamera.setPreviewDisplay(surfaceHolder); 出现的错误
     * 错误信息：Could not find class 'android.graphics.drawable.RippleDrawable', referenced from method android.support.v7.widget.AppCompatImageHelper.hasOverlappingRendering
     */

    private SurfaceView surfaceView;

    private SurfaceHolder surfaceHolder;

    private Camera mCamera;

    /**
     * 不增加的：mCamera.setPreviewTexture(textureView.getSurfaceTexture()); 出现的错误
     * Could not find class 'android.graphics.drawable.RippleDrawable', referenced from method android.support.v7.widget.AppCompatImageHelper.hasOverlappingRendering
     */
    private TextureView textureView;

    /**
     *
     03-14 17:43:19.220 24450-24450/com.yizhen.testcamera E/HW-JPEG-DEC: [HME_JPEG_DEC_Delete](3321): HME_JPEG_DEC_Delete: decoder_ctx=null
     03-14 17:43:19.303 24450-24450/com.yizhen.testcamera E/Camera: Camera new cameraInitNormal:0
     03-14 17:43:23.621 1262-1459/? E/HwCHRWebMonitor: running processName=com.yizhen.testcamera
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

//        surfaceViewType();

        applyCameraPermission();

//        textureViewType();


        try {
            mCamera = mCamera.open();
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    Log.e("zhen", "data="+data.length);
                }
            });

//                    mCamera.setPreviewTexture(textureView.getSurfaceTexture());
            mCamera.startPreview();

        } catch (Exception e) {
            Toast.makeText(CameraActivity.this, "相机打开失败", Toast.LENGTH_SHORT).show();
        }

    }


    private void applyCameraPermission(){//申请摄像头权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//已经授权

        } else {
            int CAMERA_REQUREST =0;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUREST);//1 can be another integer
        }
    }

    /**
     * TextureView 预览图片
     */
    private void textureViewType() {
        textureView = (TextureView) findViewById(R.id.textureView);

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                try {
                    mCamera = mCamera.open();
                    mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                        @Override
                        public void onPreviewFrame(byte[] data, Camera camera) {
                            Log.e("zhen", "data="+data.length);
                        }
                    });

                    mCamera.setPreviewTexture(textureView.getSurfaceTexture());
                    mCamera.startPreview();

                } catch (Exception e) {
                    Toast.makeText(CameraActivity.this, "相机打开失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {

                Toast.makeText(CameraActivity.this, "相机关闭", Toast.LENGTH_SHORT).show();
                mCamera.stopPreview();



                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }


    /**
     * SurfaceView 预览图片
     */
    private void surfaceViewType() {
//        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    mCamera = mCamera.open();
                    mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                        @Override
                        public void onPreviewFrame(byte[] data, Camera camera) {
                            Log.e("zhen", "data="+data.length);
                        }
                    });

                    mCamera.setPreviewDisplay(surfaceHolder);
                    mCamera.startPreview();

                } catch (Exception e) {
                    Toast.makeText(CameraActivity.this, "相机打开失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(mCamera!=null){

                }

            }
        });
    }
}
