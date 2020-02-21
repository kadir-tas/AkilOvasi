package com.company.akilovasi.ui.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.databinding.ItemListMainBinding;
import com.company.akilovasi.ui.main.callbacks.BannerListCallback;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder>/* implements
        ListPreloader.PreloadModelProvider<String>*/{
//    private RequestManager requestManager;
    private List<Banner> banners;
//    private ViewPreloadSizeProvider<String> preloadSizeProvider;

//    private final BannerListCallback bannerListCallback;
//    private LayoutInflater layoutInflater;

    public BannerAdapter(/*@NonNull BannerListCallback bannerListCallback, RequestManager requestManager, ViewPreloadSizeProvider<String> viewPreloader*/) {
        banners = new ArrayList<>();
//        this.requestManager = requestManager;
//        this.preloadSizeProvider = viewPreloader;
//        this.bannerListCallback = bannerListCallback;
    }

    public void setData(List<Banner> banners) {
        this.banners = banners;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        ItemListMainBinding itemListMainBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_list_main, viewGroup, false);
        BannerViewHolder holder = new BannerViewHolder(itemListMainBinding.getRoot(), itemListMainBinding/*, bannerListCallback*/);
        return holder;
//        return new BannerViewHolder(itemListMainBinding,bannerListCallback);
    }

    @Override
    public void onBindViewHolder(BannerViewHolder viewHolder, int i) {

        Banner currentBanner = banners.get(i);
        viewHolder.itemListMainBinding.setBanner(currentBanner);
        viewHolder.itemListMainBinding.executePendingBindings();
        viewHolder.onBind(banners.get(i));

//        viewHolder.onBind(banners.get(i));
    }

    @Override
    public int getItemCount() {
        if(banners == null)
            return 0;
        return banners.size();
    }

//    @NonNull
//    @Override
//    public List<String> getPreloadItems(int position) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String item) {
//        return null;
//    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        private ItemListMainBinding itemListMainBinding;

//        private BannerListCallback bannerListCallback;

        public BannerViewHolder(View rowView, ItemListMainBinding itemListMainBinding/*, BannerListCallback callback*/) {
            super(rowView);
            this.itemListMainBinding = itemListMainBinding;
//            this.bannerListCallback = callback;
//            itemListMainBinding.getRoot().setOnClickListener(v ->
//                    callback.onBannerClicked(itemListMainBinding.getBanner(), itemListMainBinding.imageViewCover));
        }

        public void onBind(Banner banner) {

            itemListMainBinding.setBanner(banner);
            Banner.loadImage(itemListMainBinding.imageViewCover,banner.getImageUrl());
            itemListMainBinding.executePendingBindings();
        }
    }
}
