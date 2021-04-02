package com.hql.service;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hql.sdk.control.SDKManger;
import com.hql.sdk.utils.LoggerUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoggerUtil.d("ceshi", "hql <<Activity<<<<<Object :" + SDKManger.getInstance().getTest()

                + ">>PID>" + android.os.Process.myPid());
    }
}
