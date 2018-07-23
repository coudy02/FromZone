package com.yizhen.testokhttp.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yizhen.testokhttp.R;
import com.yizhen.testokhttp.utils.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/7/18.
 */

public class ListPhotoAdapter extends RecyclerView.Adapter<ListPhotoAdapter.PhotoHolder>  {

    private final int SUCCESS_GETPHOTO = 2001;
    private final int FAIL_GETPHOTO = 2002;
    private ArrayList<String> mPhotoList;
    private OkHttpClient mOkHttpClient;

    public ListPhotoAdapter(ArrayList<String> photoList, OkHttpClient okHttpClient){
        this.mPhotoList = photoList;
        this.mOkHttpClient = okHttpClient;
    }

    @Override
    public ListPhotoAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        inflate(Context context, @LayoutRes int resource, ViewGroup root)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, null);

        PhotoHolder photoHolder = new PhotoHolder(view);

        return photoHolder;
    }

    @Override
    public void onBindViewHolder(ListPhotoAdapter.PhotoHolder holder, int position) {

        String url = mPhotoList.get(position);

        // 下载图片
        downPhoto(url, holder.iv_item_photo);
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    int count = 0;

    android.os.Handler mHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS_GETPHOTO:{
                    ViewAndByte vAndB = (ViewAndByte) msg.obj;
                    ImageView img = vAndB.getIm();
                    byte[] bytes = vAndB.getBytes();
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    img.setImageBitmap(bm);

                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LogUtil.setLog(null, "count", count);
                            count++;
                        }
                    });

                    break;
                }
                case FAIL_GETPHOTO:{


                    break;
                }
            }
        }
    };

    private void downPhoto(String url, final ImageView view){

        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.what = FAIL_GETPHOTO;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.setLog("", "adapter", response.body().toString());
                byte[] photoByte = response.body().bytes();
                ViewAndByte vAndB = new ViewAndByte(view, photoByte);
                Message msg = new Message();
                        msg.what = SUCCESS_GETPHOTO;
                        msg.obj = vAndB;
                mHandler.sendMessage(msg);
            }
        });
    }

    class ViewAndByte{
        private ImageView im;
        private byte[] bytes;

        public ViewAndByte() {
        }

        public ViewAndByte(ImageView im, byte[] bytes) {
            this.im = im;
            this.bytes = bytes;
        }

        public ImageView getIm() {
            return im;
        }

        public void setIm(ImageView im) {
            this.im = im;
        }

        public byte[] getBytes() {
            return bytes;
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }
    }

    class PhotoHolder extends RecyclerView.ViewHolder{

        ImageView iv_item_photo;

        public PhotoHolder(View itemView) {
            super(itemView);
            if(iv_item_photo == null){
                iv_item_photo = itemView.findViewById(R.id.iv_item_photo);
            }
        }
    }

    class PhotoListener implements  View.OnClickListener, View.OnLongClickListener{

        public PhotoListener(){

        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }



}
