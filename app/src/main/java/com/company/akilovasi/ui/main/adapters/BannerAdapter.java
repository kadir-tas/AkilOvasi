package com.company.akilovasi.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.databinding.ItemListMainBinding;
import com.company.akilovasi.ui.BaseAdapter;
import com.company.akilovasi.ui.main.callbacks.ItemBannerClick;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends BaseAdapter<BannerAdapter.BannerViewHolder, Banner> {

    private List<Banner> banners;

    private final ItemBannerClick itemBannerClick;

    public BannerAdapter(@NonNull ItemBannerClick itemBannerClick) {
        banners = new ArrayList<>();
        this.itemBannerClick = itemBannerClick;
    }

    @Override
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
        BannerViewHolder holder = new BannerViewHolder(itemListMainBinding.getRoot(), itemListMainBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BannerViewHolder viewHolder, int i) {
        viewHolder.onBind(banners.get(i), itemBannerClick);
    }

    @Override
    public int getItemCount() {
        if(banners == null)
            return 0;
        return banners.size();
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        private ItemListMainBinding itemListMainBinding;

        public BannerViewHolder(View rowView, ItemListMainBinding itemListMainBinding) {
            super(rowView);
            this.itemListMainBinding = itemListMainBinding;
        }

        public void onBind(Banner banner, ItemBannerClick itemBannerClick) {
            itemListMainBinding.setBanner(banner);
            itemListMainBinding.setItemBannerClick(itemBannerClick);
            itemListMainBinding.executePendingBindings();
        }
    }
}
