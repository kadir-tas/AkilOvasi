package com.company.akilovasi.ui.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.databinding.ItemListMainBinding;
import com.company.akilovasi.ui.BaseAdapter;
import com.company.akilovasi.ui.main.callbacks.BannerListCallback;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends BaseAdapter<BannerAdapter.BannerViewHolder, Banner> {

    private List<Banner> banners;

    private final BannerListCallback bannerListCallback;

    public BannerAdapter(@NonNull BannerListCallback bannerListCallback) {
        banners = new ArrayList<>();
        this.bannerListCallback = bannerListCallback;
    }

    @Override
    public void setData(List<Banner> banners) {
        this.banners = banners;
        notifyDataSetChanged();
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return BannerViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup, bannerListCallback);
    }

    @Override
    public void onBindViewHolder(BannerViewHolder viewHolder, int i) {
        viewHolder.onBind(banners.get(i));
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {

        ItemListMainBinding binding;

        public static BannerViewHolder create(LayoutInflater inflater, ViewGroup parent, BannerListCallback callback) {
            ItemListMainBinding itemListMainBinding = ItemListMainBinding.inflate(inflater, parent, false);
            return new BannerViewHolder(itemListMainBinding, callback);
        }


        public BannerViewHolder(ItemListMainBinding binding, BannerListCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onBannerClicked(binding.getBanner(), binding.imageViewCover));
        }

        public void onBind(Banner banner) {
            binding.setBanner(banner);
            binding.executePendingBindings();
        }
    }
}
