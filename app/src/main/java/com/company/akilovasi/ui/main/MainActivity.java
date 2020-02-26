package com.company.akilovasi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.databinding.ActivityMainBinding;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.main.adapters.BannerAdapter;
import com.company.akilovasi.ui.main.callbacks.BannerListCallback;
import com.company.akilovasi.ui.plant.PlantCategoryActivity;

import java.util.List;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements BannerListCallback {

    private BannerAdapter mBannerAdapter;

    private RecyclerView mRecyclerView;

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
        startActivity(new Intent(MainActivity.this, PlantCategoryActivity.class));
        finish();
//        ActivityMainBinding activityMainBinding =
//                DataBindingUtil.setContentView(this, R.layout.activity_main);
//        initGlide();
        initRecyclerView();

//        recyclerView = activityMainBinding.recyclerView;
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);

//        bannerAdapter = new BannerAdapter(this);
//        recyclerView.setAdapter(bannerAdapter);




//        dataBinding.recyclerView.setAdapter(new BannerAdapter(this));
        viewModel.getAllBanners()
                .observe(this, new Observer<Resource<List<Banner>>>() {
                    @Override
                    public void onChanged(Resource<List<Banner>> listResource) {
                        Log.v("MSGG", listResource.message+"");
                        Log.v("MSGG", listResource.data+"");
                        Log.v("MSGG", listResource.status+"");
                        mBannerAdapter.setData(listResource.data);
                    }
                });
//        viewModel.getAllBanners()
//                .observe(this, listResource -> {
//                    Log.v("MSGG", listResource.message+"");
//                    Log.v("MSGG", listResource.data+"");
//                    Log.v("MSGG", listResource.status+"");
//                    if(listResource.data == null || listResource.data.isEmpty()){
//                        viewModel.initBanner();
//                    }
//                    mBannerAdapter.setData(listResource.data);
//                });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    private void initRecyclerView(){
//        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();
//        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView = dataBinding.recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

//        RecyclerViewPreloader<String> preloader = new RecyclerViewPreloader<String>(
//                Glide.with(this),
//                mBannerAdapter,
//                viewPreloader,
//                30);

//        mRecyclerView.addOnScrollListener(preloader);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                if(!mRecyclerView.canScrollHorizontally(1)
//                        && viewModel.getViewstate().getValue() == RecipeListViewModel.ViewState.RECIPES){
//                    mRecipeListViewModel.searchNextPage();
//                }
//            }
//        });
        mBannerAdapter = new BannerAdapter(/*this, initGlide(), viewPreloader*/);
        mRecyclerView.setAdapter(mBannerAdapter);
    }

    private RequestManager initGlide() {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }


    @Override
    public void onBannerClicked(Banner banner, View sharedView) {

    }
}

