package com.yizhen.testsharepreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 发送方  sharePreference 存储方
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tv_content;
    private Button btn_write,btn_clear;
    public static int MODE = Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("mySp", MODE);
        editor=sp.edit();
        tv_content= (TextView) findViewById(R.id.tv_content);
        btn_write= (Button) findViewById(R.id.btn_write);
        btn_write.setOnClickListener(this);
        btn_clear= (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        String content=sp.getString("content","");
        tv_content.setText(content);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_write:
                editor.putString("content",getString(R.string.content));
                editor.commit();
                String content = sp.getString("content","");
                tv_content.setText(content);
                break;
            case R.id.btn_clear:
                editor.putString("content","");
                editor.commit();
                String content2=sp.getString("content","");
                tv_content.setText(content2);
                break;
            default:
                break;

        }
    }




}
