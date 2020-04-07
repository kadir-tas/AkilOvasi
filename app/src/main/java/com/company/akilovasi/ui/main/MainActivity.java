package com.company.akilovasi.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.databinding.ActivityMainBinding;
import com.company.akilovasi.di.SecretPrefs;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.common.fullscreen.PlantFullImageFragment;
import com.company.akilovasi.ui.login.LoginActivity;
import com.company.akilovasi.ui.main.adapters.BannerAdapter;
import com.company.akilovasi.ui.main.adapters.PlantAdapter;
import com.company.akilovasi.ui.main.callbacks.AddPlantClick;
import com.company.akilovasi.ui.main.callbacks.ItemBannerClick;
import com.company.akilovasi.ui.main.callbacks.ItemPlantClick;
import com.company.akilovasi.ui.main.callbacks.LogoutButtonClick;
import com.company.akilovasi.ui.main.callbacks.ProfileButtonClick;
import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragment;
import com.company.akilovasi.ui.main.fragments.profile.ProfileFragment;
import com.company.akilovasi.ui.plant.PlantCategoryActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import static com.company.akilovasi.data.remote.ApiConstants.ACCESS_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.REFRESH_TOKEN;


public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements ItemBannerClick, ItemPlantClick, AddPlantClick, LogoutButtonClick, ProfileButtonClick {

    private static final String TAG = "MainActivity";
    private BannerAdapter mBannerAdapter;
    private PlantAdapter mPlantAdapter;

    private RecyclerView mBannerRecyclerView;
    private RecyclerView mPlantsRecyclerView;

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    Picasso picasso;

    private boolean plantsLoaded = false;
    private boolean bannersLoaded = false;

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
        super.onCreate(savedInstanceState);
        dataBinding.waitScreen.setVisibility(View.VISIBLE);
        dataBinding.content.wrapper.setAddPlantClick(this);
        dataBinding.leftMenu.setLogoutClick(this);
        dataBinding.leftMenu.setProfileClick(this);
        initBannerRecyclerView();
        initPlantRecyclerView();
        subscribeObservers();

    }

    @Override
    protected DataBindingComponent getDataBindingComponent() {
        return null;
    }

    private void subscribeObservers() {
        viewModel.getAllActiveBanners()
                .observe(this, listResource -> {
                    Log.v("MSGG", listResource.message + "");
                    Log.v("MSGG", listResource.data + "");
                    Log.v("MSGG", listResource.status + "");
                    mBannerAdapter.setData(listResource.data);
                    viewModel.getAllActiveBanners().removeObservers(MainActivity.this);
                    plantsLoaded = true;
                    if (bannersLoaded) {
                        dataBinding.waitScreen.setVisibility(View.INVISIBLE);
                    }

                });

        viewModel.getAllPlants()
                .observe(this, listResource -> {
                    if (listResource.data != null && listResource.data.size() == 0) {
                        dataBinding.content.wrapper.plantRecyclerView.noPlantFrameLayout.setVisibility(View.VISIBLE);
                        dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView.setVisibility(View.INVISIBLE);
                        bannersLoaded = true;
                        if (plantsLoaded) {
                            dataBinding.waitScreen.setVisibility(View.INVISIBLE);
                        }
                    } else if (listResource.data != null) {
                        dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView.setVisibility(View.VISIBLE);
                        dataBinding.content.wrapper.plantRecyclerView.noPlantFrameLayout.setVisibility(View.INVISIBLE);
                        mPlantAdapter.setData(listResource.data);
                        viewModel.getAllPlants().removeObservers(MainActivity.this);
                        dataBinding.waitScreen.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void initBannerRecyclerView() {

        mBannerRecyclerView = dataBinding.content.wrapper.recyclerView;
        mBannerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mBannerRecyclerView);
        mBannerRecyclerView.setHasFixedSize(true);

        mBannerAdapter = new BannerAdapter(this, picasso);
        mBannerRecyclerView.setAdapter(mBannerAdapter);
    }

    private void initPlantRecyclerView() {

        mPlantsRecyclerView = dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView;
        mPlantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPlantsRecyclerView.setHasFixedSize(true);

        mPlantAdapter = new PlantAdapter(this, picasso);
        mPlantsRecyclerView.setAdapter(mPlantAdapter);
    }

    @Override
    public void onBannerClicked(Banner banner) {
        if(banner.getBannerLinkType().equals("link")){
            String url = banner.getBannerLink();
            if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://" + url;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }


    @Override
    public void onPlantClick(Long userPlantId) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
        if (f != null) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, PlantHistoryFragment.newInstance(userPlantId), PlantHistoryFragment.TAG).addToBackStack(null).commit();
    }

    @Override
    public void onPlantImageClick(Long userPlantId) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(PlantFullImageFragment.TAG);
        if(f != null){
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
        PlantFullImageFragment fragment = new PlantFullImageFragment(PlantFullImageFragment.USER_PLANT, userPlantId);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, PlantFullImageFragment.TAG).commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
        Fragment f2 = getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
        Fragment f3 = getSupportFragmentManager().findFragmentByTag(PlantFullImageFragment.TAG);
        if(f3 != null){
            getSupportFragmentManager().beginTransaction().remove(f3).commit();
        }else if (f != null) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        } else if (f2 != null) {
            dataBinding.main.closeDrawer(Gravity.LEFT);
            getSupportFragmentManager().beginTransaction().remove(f2).commit();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onAddPlantClick() {
//        dataBinding.main.openDrawer(Gravity.LEFT);
//        dataBinding.leftMenu.menu.transitionToStart();
//        dataBinding.leftMenu.menu.transitionToEnd();

//        dataBinding.main.setState(R.id.end, ConstraintSet.WRAP_CONTENT, ConstraintSet.WRAP_CONTENT);

        startActivity(new Intent(MainActivity.this, PlantCategoryActivity.class));
        finish();
    }

    @Override
    public void onLogoutButtonClicked() {

        viewModel.logout().observe(MainActivity.this, responseResource -> {
            if (responseResource != null) {
                switch (responseResource.status) {
                    case SUCCESS:
                        if (responseResource.data != null && responseResource.data.getSuccess()) {
                            secretPreferences.edit().remove(ACCESS_TOKEN).apply();
                            secretPreferences.edit().remove(REFRESH_TOKEN).apply();
                            secretPreferences.edit().remove(ApiConstants.USER_ID).apply();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case ERROR:
                        Log.e(TAG, "onChanged: Error" + responseResource.message);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case LOADING:
                        Log.d(TAG, "onChanged: Loading...");
                        break;
                }
            }
        });
    }

    @Override
    public void onProfileButtonClicked() {
        Fragment f = getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
        if (f == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ProfileFragment.newInstance(), ProfileFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProfileFragment.newInstance(), ProfileFragment.TAG).commit();
        }
    }
}

