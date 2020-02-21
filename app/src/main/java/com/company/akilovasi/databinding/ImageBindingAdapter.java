package com.company.akilovasi.databinding;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.akilovasi.BuildConfig;
import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.ApiConstants;
import com.squareup.picasso.Picasso;

public final class ImageBindingAdapter {

    @BindingAdapter({ "imageUrl" })
    public static void loadImage(ImageView imageView, String imageUrl) {

        if (imageUrl != null && !imageUrl.equals("")) {
            Picasso.get()
                    .load(BuildConfig.BASE_URL + ApiConstants.BANNER_IMAGE_ENDPOINT_PREFIX+imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
            Log.d("CCC",ApiConstants.BANNER_IMAGE_ENDPOINT_PREFIX+imageUrl);
        }
    }
//        Log.d("CCC",imageUrl);
//        Glide.with(imageView.getContext())
//                .setDefaultRequestOptions(new RequestOptions()
//                        .circleCrop())
//                .load(ApiConstants.BANNER_IMAGE_ENDPOINT_PREFIX + imageUrl)
//                .placeholder(R.drawable.loading)
//                .into(imageView);
//        Log.v("AAA","HEREEEEEEEE " + imageUrl);
//    }
}