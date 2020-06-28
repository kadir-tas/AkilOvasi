package com.company.akilovasi.databinding.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.company.akilovasi.BuildConfig;
import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.ApiConstants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class BlogImageBindingAdapter {
    private Picasso picasso;

    public BlogImageBindingAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    @BindingAdapter({"blogImage"})
    public void loadImage(ImageView imageView, String blogId) {
        if (blogId != null && !blogId.equals(ApiConstants.EMPTY_STRING)) {
            picasso.load(BuildConfig.BASE_URL + ApiConstants.BLOG_IMAGE_ENDPOINT_PREFIX +blogId).error(R.drawable.ic_photo_black_24dp).resize(500, 500)
                    .networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    picasso.load(BuildConfig.BASE_URL + ApiConstants.BLOG_IMAGE_ENDPOINT_PREFIX + blogId)
                            .placeholder(R.drawable.ic_photo_black_24dp)
                            .into(imageView);
                }
            });
        }
    }
}
