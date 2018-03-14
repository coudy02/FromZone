package com.yizhen.testcamera;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Date;

public class BackgroundVideoRecorder extends Service implements SurfaceHolder.Callback {

    private WindowManager windowManager;
    private SurfaceView surfaceView;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null;

    @Override
    public void onCreate() {

        // Start foreground service to avoid unexpected kill
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Background Video Recorder")
                .setContentText("")
                .setSmallIcon(R.drawable.button_selector)
                .build();
        startForeground(1234, notification);

        // Create new SurfaceView, set its size to 1x1, move it to the top left corner and set this service as a callback
        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        surfaceView = new SurfaceView(this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                1, 1,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );

//全屏显示在最上图层上
//        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//                PixelFormat.TRANSLUCENT
//        );


        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowManager.addView(surfaceView, layoutParams);
        surfaceView.getHolder().addCallback(this);

        initCamera();

    }

    private void initCamera(){
        camera = Camera.open();
        mediaRecorder = new MediaRecorder();
        camera.unlock();

        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Log.e("zhen", data.length+"----------");
            }
        });

//        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        mediaRecorder.setOutputFile(
                Environment.getExternalStorageDirectory() + "/" +
                        DateFormat.format("yyyy-MM-dd_kk-mm-ss", new Date().getTime()) +
                        ".mp4"
        );

        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
        }
        mediaRecorder.start();
    }

    // Method called right after Surface created (initializing and starting MediaRecorder)
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        camera = Camera.open();
        mediaRecorder = new MediaRecorder();
        camera.unlock();

        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Log.e("zhen", data.length+"----------");
            }
        });

//        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        mediaRecorder.setOutputFile(
                Environment.getExternalStorageDirectory() + "/" +
                        DateFormat.format("yyyy-MM-dd_kk-mm-ss", new Date().getTime()) +
                        ".mp4"
        );

        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
        }
        mediaRecorder.start();

    }

    // Stop recording and remove SurfaceView
    @Override
    public void onDestroy() {

        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();

        camera.lock();
        camera.release();

        windowManager.removeView(surfaceView);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
