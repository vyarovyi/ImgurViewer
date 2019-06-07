package com.imgur.viewer.activities;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initViews();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            return;
        }

        initViews();
    }
}
