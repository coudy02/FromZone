package com.yizhen.testoutofmemory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView  tv_hello = findViewById(R.id.tv_hello);
        tv_hello.setOnClickListener(this);




    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, OneActivity.class);
        startActivity(intent);

    }
}
