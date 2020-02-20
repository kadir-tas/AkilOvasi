package com.company.akilovasi.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.databinding.ActivityMainBinding;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.main.adapters.BannerAdapter;
import com.company.akilovasi.ui.main.callbacks.BannerListCallback;

import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements BannerListCallback {

    @Override
    public Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerView.setAdapter(new BannerAdapter(this));

        viewModel.getAllBanners()
                .observe(this, listResource -> dataBinding.setResource(listResource));
    }

    @Override
    public void onBannerClicked(Banner banner, View sharedView) {

    }
}
