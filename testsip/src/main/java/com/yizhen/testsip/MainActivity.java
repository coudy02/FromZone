package com.yizhen.testsip;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    public SipManager mSipManager = null;
    public SipProfile mSipProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建 SipManager 对象
        if(mSipManager == null){
            mSipManager = SipManager.newInstance(this);
        }


        String username = "1023";
        String domain = "10.19.155.102";
        String password = "123456ab";
        try {
            SipProfile.Builder builder = new SipProfile.Builder(username, domain);
            builder.setPassword(password);
            mSipProfile = builder.build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setAction("android.SipDemo.INCOMING_CALL");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, Intent.FILL_IN_DATA);
        try {
            // 把 sipProfile 注册到服务器，并发起一个呼叫
            mSipManager.open(mSipProfile, pendingIntent, null);
        } catch (SipException e) {
            e.printStackTrace();
        }

        try {
            mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {
                @Override
                public void onRegistering(String localProfileUri) {

                }

                @Override
                public void onRegistrationDone(String localProfileUri, long expiryTime) {

                }

                @Override
                public void onRegistrationFailed(String localProfileUri, int errorCode, String errorMessage) {

                }
            });
        } catch (SipException e) {
            e.printStackTrace();
        }

    }
}
