package com.company.akilovasi.ui;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.company.akilovasi.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<DB extends ViewDataBinding> extends DaggerAppCompatActivity {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;

    public DB dataBinding;

    @LayoutRes
    public abstract int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes());
    }


}