package com.company.akilovasi.databinding;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.ApiConstants;
import com.squareup.picasso.Picasso;

public final class ImageBindingAdapter {

    @BindingAdapter(value = "url")
    public static void loadImageUrl(ImageView view, String url) {
        if (url != null && !url.equals("")) {
            Picasso.get()
                    .load(ApiConstants.IMAGE_ENDPOINT_PREFIX)
                    .placeholder(R.drawable.placeholder)
                    .into(view);
        }
        Log.v("BBB", url);

    }
}