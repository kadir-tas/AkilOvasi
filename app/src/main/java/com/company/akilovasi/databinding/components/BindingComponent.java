package com.company.akilovasi.databinding.components;

import androidx.databinding.DataBindingComponent;

import com.company.akilovasi.databinding.adapters.BannerImageBindingAdapter;
import com.company.akilovasi.databinding.adapters.BlogImageBindingAdapter;
import com.company.akilovasi.databinding.adapters.HistoricalPlantImageBindingAdapter;
import com.company.akilovasi.databinding.adapters.NotificationBindingAdapter;
import com.company.akilovasi.databinding.adapters.PlantTypeImageBindingAdapter;
import com.company.akilovasi.databinding.adapters.ShopItemImageBindingAdapter;
import com.company.akilovasi.databinding.adapters.SupportTicketBindingAdapter;
import com.company.akilovasi.databinding.adapters.UserPlantsImageBindingAdapter;
import com.squareup.picasso.Picasso;

public class BindingComponent implements DataBindingComponent {

    private Picasso picasso;

    public BindingComponent(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public PlantTypeImageBindingAdapter getPlantTypeImageBindingAdapter() {
        return new PlantTypeImageBindingAdapter(picasso);
    }

    @Override
    public BannerImageBindingAdapter getBannerImageBindingAdapter() {
        return new BannerImageBindingAdapter(picasso);
    }

    @Override
    public UserPlantsImageBindingAdapter getUserPlantsImageBindingAdapter() {
        return new UserPlantsImageBindingAdapter(picasso);
    }

    @Override
    public HistoricalPlantImageBindingAdapter getHistoricalPlantImageBindingAdapter() {
        return new HistoricalPlantImageBindingAdapter(picasso);
    }

    @Override
    public NotificationBindingAdapter getNotificationBindingAdapter() {
        return new NotificationBindingAdapter();
    }

    @Override
    public SupportTicketBindingAdapter getSupportTicketBindingAdapter() {
        return new SupportTicketBindingAdapter();
    }

    @Override
    public BlogImageBindingAdapter getBlogImageBindingAdapter(){
        return new BlogImageBindingAdapter(picasso);
    }

    @Override
    public ShopItemImageBindingAdapter getShopItemImageBindingAdapter(){
        return new ShopItemImageBindingAdapter(picasso);
    }
}
