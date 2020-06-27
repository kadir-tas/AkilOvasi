package com.company.akilovasi.databinding.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.company.akilovasi.BuildConfig;
import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.ApiConstants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ShopItemImageBindingAdapter {
    private Picasso picasso;

    public ShopItemImageBindingAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    @BindingAdapter({"shopItemImage"})
    public void loadImage(ImageView imageView, String shopItemId) {
        if (shopItemId != null && !shopItemId.equals(ApiConstants.EMPTY_STRING)) {
            picasso.load(BuildConfig.BASE_URL + ApiConstants.BLOG_IMAGE_ENDPOINT_PREFIX + shopItemId).error(R.drawable.ic_photo_black_24dp).resize(500, 500)
                    .networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    picasso.load(BuildConfig.BASE_URL + ApiConstants.HISTORIC_PLANTS_IMAGE_ENDPOINT_PREFIX + shopItemId)
                            .placeholder(R.drawable.ic_photo_black_24dp)
                            .into(imageView);
                }
            });
        }
    }
}
