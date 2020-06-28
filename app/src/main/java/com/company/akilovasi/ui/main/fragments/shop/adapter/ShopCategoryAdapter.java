package com.company.akilovasi.ui.main.fragments.shop.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.company.akilovasi.R;
import com.company.akilovasi.databinding.ItemShopCategoryBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.main.fragments.shop.callback.OnShopItemSubCategoryClicked;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ShopCategoryAdapter extends RecyclerView.Adapter<ShopCategoryAdapter.ShopItemViewHolder> {

    private List<Pair< String, Long>> shopItems;

    private Picasso picasso;
    private OnShopItemSubCategoryClicked onShopItemSubCategoryClicked;
    public ShopCategoryAdapter(Picasso picasso, OnShopItemSubCategoryClicked onShopItemSubCategoryClicked) {
        this.picasso = picasso;
        shopItems = new ArrayList<>();
        this.onShopItemSubCategoryClicked = onShopItemSubCategoryClicked;
    }

    public void setData(List<Pair< String, Long>> shopItems){
        this.shopItems = shopItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShopCategoryBinding itemShopCategoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_shop_category, parent, false, new BindingComponent(picasso));
        return new ShopCategoryAdapter.ShopItemViewHolder(itemShopCategoryBinding.getRoot(), itemShopCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemViewHolder holder, int position) {
       holder.itemShopCategoryBinding.setOnSubCategoryClicked(onShopItemSubCategoryClicked);
       holder.itemShopCategoryBinding.setShopItemId( shopItems.get(position).second );
       holder.itemShopCategoryBinding.setShopItemSubCategoryName( shopItems.get(position).first );
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    static class ShopItemViewHolder extends RecyclerView.ViewHolder{
        ItemShopCategoryBinding itemShopCategoryBinding;
        public ShopItemViewHolder(@NonNull View itemView, ItemShopCategoryBinding itemShopCategoryBinding) {
            super(itemView);
            this.itemShopCategoryBinding = itemShopCategoryBinding;
        }
    }
}