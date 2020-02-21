package com.company.akilovasi.databinding;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.ApiConstants;
import com.squareup.picasso.Picasso;

public final class ImageBindingAdapter {

    @BindingAdapter({ "imageUrl" })
    public static void loadImage(ImageView imageView, String imageUrl) {
        Log.d("CCC",imageUrl);
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .circleCrop())
                .load(ApiConstants.BANNER_IMAGE_ENDPOINT_PREFIX + imageUrl)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}