package com.company.akilovasi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.databinding.ActivityMainBinding;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.main.adapters.BannerAdapter;
import com.company.akilovasi.ui.main.adapters.PlantAdapter;
import com.company.akilovasi.ui.main.callbacks.AddPlantClick;
import com.company.akilovasi.ui.main.callbacks.ItemBannerClick;
import com.company.akilovasi.ui.main.callbacks.ItemPlantClick;
import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragment;
import com.company.akilovasi.ui.plant.PlantCategoryActivity;


public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements ItemBannerClick, ItemPlantClick, AddPlantClick {

    private BannerAdapter mBannerAdapter;
    private PlantAdapter mPlantAdapter;

    private RecyclerView mBannerRecyclerView;
    private RecyclerView mPlantsRecyclerView;


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
        initBannerRecyclerView();
        initPlantRecyclerView();
        subscribeObservers();

    }

    private void subscribeObservers() {
        viewModel.getAllBanners()
                .observe(this, listResource -> {
                    Log.v("MSGG", listResource.message + "");
                    Log.v("MSGG", listResource.data + "");
                    Log.v("MSGG", listResource.status + "");
                    mBannerAdapter.setData(listResource.data);
                });

        viewModel.getAllPlants().observe(this, listResource -> {
            mPlantAdapter.setData(listResource.data);
        });
    }


    private void initBannerRecyclerView(){

        mBannerRecyclerView = dataBinding.content.wrapper.recyclerView;
        mBannerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mBannerRecyclerView);
        mBannerRecyclerView.setHasFixedSize(true);

        mBannerAdapter = new BannerAdapter(this);
        mBannerRecyclerView.setAdapter(mBannerAdapter);
    }

    private void initPlantRecyclerView(){

        mPlantsRecyclerView = dataBinding.content.wrapper.plantRecyclerView.plantRecyclerView;
        mPlantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPlantsRecyclerView.setHasFixedSize(true);

        mPlantAdapter = new PlantAdapter(this );
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
        if(f == null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, PlantHistoryFragment.newInstance(userPlantId), PlantHistoryFragment.TAG).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PlantHistoryFragment.newInstance(userPlantId), PlantHistoryFragment.TAG).commit();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentByTag(PlantHistoryFragment.TAG);
        if(f != null){
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onAddPlantClick() {
        startActivity(new Intent(MainActivity.this, PlantCategoryActivity.class));
        finish();
    }
}

