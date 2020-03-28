package com.company.akilovasi.databinding.adapters;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.company.akilovasi.BuildConfig;
import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.ApiConstants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class HistoricalPlantImageBindingAdapter {

    Picasso picasso;

    public HistoricalPlantImageBindingAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    @BindingAdapter({"historicalImage"})
    public void loadImage(ImageView imageView, String imageUrl) {

        if (imageUrl != null && !imageUrl.equals(ApiConstants.EMPTY_STRING)) {
            picasso.load(BuildConfig.BASE_URL + ApiConstants.HISTORIC_PLANTS_IMAGE_ENDPOINT_PREFIX +imageUrl).error(R.drawable.ic_photo_black_24dp).resize(500, 500)
                    .networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("CACHEEE","IMAGE FROM CACHE");
                }

                @Override
                public void onError(Exception e) {
                    picasso.load(BuildConfig.BASE_URL + ApiConstants.HISTORIC_PLANTS_IMAGE_ENDPOINT_PREFIX +imageUrl)
                            .placeholder(R.drawable.ic_photo_black_24dp)
                            .into(imageView);
                    Log.d("CACHEEE","IMAGE IS NOT FROM CACHE");


                }
            });
            Log.d("CCC",BuildConfig.BASE_URL + ApiConstants.HISTORIC_PLANTS_IMAGE_ENDPOINT_PREFIX+imageUrl);
        }
    }

}
