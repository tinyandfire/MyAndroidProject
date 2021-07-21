package com.example.main_core;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.android_module.basecore.SimpleActivity;


/**
 * author : Cong
 * date   : 2020-09-22
 */
public class MainActivity extends SimpleActivity {

    private View tvMain;
//    private static String HOST = "com.example.myapptext";
//    private static String scheme = "scheme://%s/other";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView() {
        tvMain = findViewById(R.id.tv_main);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        Network.getInstance().getService();
    }

    @Override
    protected void initEvent() {
        tvMain.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("cong://app.test/next"));
            startActivity(intent);
        });
    }
}
