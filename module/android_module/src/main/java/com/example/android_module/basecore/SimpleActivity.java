package com.example.android_module.basecore;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.android_module.base.BaseActivity;

/**
 * author : Cong
 * date   : 2020-09-22
 */
public abstract class SimpleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        bindView();
        initData(savedInstanceState);
        initEvent();
    }

    protected abstract int setLayoutId();

    protected abstract void bindView();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initEvent();
}

