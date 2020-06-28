package com.company.akilovasi.ui.main.fragments.shop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.ShopItem;
import com.company.akilovasi.databinding.ItemShopBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.main.fragments.shop.callback.OnShopItemClicked;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShopItemsAdapter extends RecyclerView.Adapter<ShopItemsAdapter.ShopItemViewHolder> {

    private List<ShopItem> shopItems;

    private Picasso picasso;
    private OnShopItemClicked onShopItemClicked;
    public ShopItemsAdapter(Picasso picasso, OnShopItemClicked onShopItemClicked) {
        this.picasso = picasso;
        shopItems = new ArrayList<>();
        this.onShopItemClicked = onShopItemClicked;
    }

    public void setData(List<ShopItem> shopItems){
        this.shopItems = shopItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShopBinding itemShopBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_shop, parent, false, new BindingComponent(picasso));
        return new ShopItemsAdapter.ShopItemViewHolder(itemShopBinding.getRoot(), itemShopBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemViewHolder holder, int position) {
        holder.itemShopBinding.setShopItem( shopItems.get(position) );
        holder.itemShopBinding.setOnClick(onShopItemClicked);
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    static class ShopItemViewHolder extends RecyclerView.ViewHolder{
        ItemShopBinding itemShopBinding;
        public ShopItemViewHolder(@NonNull View itemView, ItemShopBinding itemShopBinding) {
            super(itemView);
            this.itemShopBinding = itemShopBinding;
        }
    }
}