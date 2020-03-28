package com.company.akilovasi.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.responses.Response;
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
import com.company.akilovasi.ui.main.callbacks.PlantHistoryClick;
import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragment;
import com.company.akilovasi.ui.plant.PlantCategoryActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import static com.company.akilovasi.data.remote.ApiConstants.ACCESS_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.REFRESH_TOKEN;


public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements ItemBannerClick, ItemPlantClick, AddPlantClick, LogoutButtonClick  {

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
        dataBinding.content.wrapper.setAddPlantClick(this);
        dataBinding.leftMenu.setLogoutClick(this);
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
                });

        viewModel.getAllPlants()
                .observe(this, listResource -> {
                    mPlantAdapter.setData(listResource.data);
                    viewModel.getAllPlants().removeObservers(MainActivity.this);
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

    //TODO:Still on development phase
    @Override
    public void onBannerClicked(Banner banner) {
        Log.d("CLICK", "BANNER CLICKED");
    }


    @Override
    public void onPlantClick(Long userPlantId) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
        if (f == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, PlantHistoryFragment.newInstance(userPlantId), PlantHistoryFragment.TAG).addToBackStack(null).commit();
        }
        /* Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
        if (f == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, PlantHistoryFragment.newInstance(userPlantId), PlantHistoryFragment.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PlantHistoryFragment.newInstance(userPlantId), PlantHistoryFragment.TAG).commit();
        }*/
    }

    @Override
    public void onPlantImageClick(Long userPlantId) {
        PlantFullImageFragment fragment = new PlantFullImageFragment(PlantFullImageFragment.USER_PLANT,userPlantId);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
        if (f != null) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public void onAddPlantClick() {
        startActivity(new Intent(MainActivity.this, PlantCategoryActivity.class));
        finish();
    }

    @Override
    public void onLogoutButtonClicked() {

        viewModel.logout().observe(MainActivity.this, new Observer<Resource<Response<Message>>>() {
            @Override
            public void onChanged(Resource<Response<Message>> responseResource) {
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
                            //TODO: I ADDED THIS TO TEST REMOVE THIS WHEN ITS DONE
                            secretPreferences.edit().remove(ACCESS_TOKEN).apply();
                            secretPreferences.edit().remove(REFRESH_TOKEN).apply();
                            secretPreferences.edit().remove(ApiConstants.USER_ID).apply();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case LOADING:
                            Log.d(TAG, "onChanged: Loading...");
                            break;
                    }
                }
            }
        });
    }
}

