package com.company.akilovasi.ui.main;

import android.os.Bundle;

import com.company.akilovasi.R;
import com.company.akilovasi.databinding.ActivityMainBinding;
import com.company.akilovasi.ui.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}