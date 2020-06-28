package com.company.akilovasi.di.modules;

import com.company.akilovasi.data.local.entities.ShopItem;
import com.company.akilovasi.ui.common.fullscreen.PlantFullImageFragment;
import com.company.akilovasi.ui.login.fragments.RegisterFragment;
import com.company.akilovasi.ui.main.fragments.blog.BlogContentFragment;
import com.company.akilovasi.ui.main.fragments.blog.BlogFragment;
import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragment;

import com.company.akilovasi.ui.main.fragments.profile.ProfileFragment;
import com.company.akilovasi.ui.main.fragments.shop.ShopCategoryFragment;
import com.company.akilovasi.ui.main.fragments.shop.ShopFragment;
import com.company.akilovasi.ui.main.fragments.shop.ShopItemsFragment;
import com.company.akilovasi.ui.main.fragments.support.SupportFragment;
import com.company.akilovasi.ui.main.fragments.support.create.SupportCreateFragment;
import com.company.akilovasi.ui.main.fragments.support.mysupports.MySupportsFragment;
import com.company.akilovasi.ui.notification.NotificationFragment;
import com.company.akilovasi.ui.plant.fragments.addplant.PlantAddFragment;
import com.company.akilovasi.ui.plant.fragments.plantcategory.PlantCategoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract PlantCategoryFragment plantCategoryFragment();

    @ContributesAndroidInjector
    abstract PlantHistoryFragment plantHistoryFragment();

    @ContributesAndroidInjector
    abstract PlantAddFragment plantAddFragment();

    @ContributesAndroidInjector
    abstract RegisterFragment registerFragment();

    @ContributesAndroidInjector
    abstract PlantFullImageFragment plantFullImageFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment profileFragment();

    @ContributesAndroidInjector
    abstract NotificationFragment notificationFragment();

    @ContributesAndroidInjector
    abstract SupportFragment supportFragment();

    @ContributesAndroidInjector
    abstract MySupportsFragment mySupportsFragment();

    @ContributesAndroidInjector
    abstract SupportCreateFragment supportCreateFragment();

    @ContributesAndroidInjector
    abstract BlogFragment blogFragment();

    @ContributesAndroidInjector
    abstract BlogContentFragment blogContentFragment();


    @ContributesAndroidInjector
    abstract ShopFragment shopFragment();

    @ContributesAndroidInjector
    abstract ShopCategoryFragment shopCategoryFragment();

    @ContributesAndroidInjector
    abstract ShopItemsFragment shopItemsFragment();

}

