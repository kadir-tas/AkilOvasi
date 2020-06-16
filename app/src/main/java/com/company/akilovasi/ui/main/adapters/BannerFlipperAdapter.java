package com.company.akilovasi.ui.main.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.databinding.ItemListMainBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.main.MainActivity;
import com.company.akilovasi.ui.main.callbacks.ItemBannerClick;
import com.company.akilovasi.util.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BannerFlipperAdapter extends BaseAdapter {
    private static final String TAG = "BannerFlipperAdapter";

    private List<Banner> banners;
    private final ItemBannerClick itemBannerClick;
    private Picasso picasso;
    private Context context;

    public BannerFlipperAdapter(Context context, Picasso picasso) {
        banners = new ArrayList<>();
        this.itemBannerClick = (MainActivity) context;
        this.context = context;
        this.picasso = picasso;
    }

    public void setData(List<Banner> banners) {
        if (banners != null && !banners.isEmpty()) {
            this.banners = banners;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (banners == null)
            return 0;
        return banners.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;
        if (convertView == null) {

            ItemListMainBinding itemListMainBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_list_main, parent, false, new BindingComponent(picasso));
            mViewHolder = new ViewHolder(itemListMainBinding, banners.get(position), itemBannerClick);
        }
        else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        return mViewHolder.getView();
    }

    class ViewHolder {

        ItemListMainBinding itemListMainBinding;

        ViewHolder(ItemListMainBinding itemListMainBinding, Banner banner, ItemBannerClick itemBannerClick) {
            this.itemListMainBinding = itemListMainBinding;
            this.itemListMainBinding.setBanner(banner);
            this.itemListMainBinding.setItemBannerClick(itemBannerClick);
            this.itemListMainBinding.executePendingBindings();
        }

        View getView(){
            return this.itemListMainBinding.getRoot();
        }
    }
}
