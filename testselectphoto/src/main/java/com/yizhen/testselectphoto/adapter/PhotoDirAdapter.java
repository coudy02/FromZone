package com.yizhen.testselectphoto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yizhen.testselectphoto.R;
import com.yizhen.testselectphoto.bean.PhotoDirBean;
import com.yizhen.testselectphoto.utils.LogUtil;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/7/13.
 */

public class PhotoDirAdapter extends RecyclerView.Adapter<PhotoDirAdapter.PhotoDirHolder>{

    ArrayList<PhotoDirBean> dirList = null;

    public PhotoDirAdapter(ArrayList<PhotoDirBean> dirList){
        this.dirList = dirList;
    }

    @Override
    public PhotoDirHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_dir, parent, false);
        PhotoDirHolder holder = new PhotoDirHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PhotoDirHolder holder, int position) {
        holder.tv_dir_name.setText(dirList.get(position).pdName);
        LogUtil.setLog(null,"pdName", dirList.get(position).pdName);
    }

    @Override
    public int getItemCount() {
        return dirList.size();
    }

    /***/
    class PhotoDirHolder extends RecyclerView.ViewHolder{

        public TextView tv_dir_name;

        public PhotoDirHolder(View itemView) {
            super(itemView);
            tv_dir_name = itemView.findViewById(R.id.tv_dir_name);
        }
    }

}
