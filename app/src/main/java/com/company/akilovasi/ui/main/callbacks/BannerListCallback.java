package com.company.akilovasi.ui.main.callbacks;

import android.view.View;

import com.company.akilovasi.data.local.entities.Banner;

public interface BannerListCallback {

    void onBannerClicked(Banner banner, View sharedView);

}
