package com.company.akilovasi.databinding.adapters;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.company.akilovasi.BuildConfig;
import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.ApiConstants;
import com.squareup.picasso.Picasso;

public class PlantTypeImageBindingAdapter {

    Picasso picasso;

    public PlantTypeImageBindingAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    @BindingAdapter({ "imageUrl" })
    public void loadImagePlantType(ImageView imageView, Long plantId) {

        if (plantId != null) {
            picasso.load(BuildConfig.BASE_URL + ApiConstants.PLANT_TYPE_IMAGE_ENDPOINT_PREFIX + plantId)
                    .placeholder(R.drawable.ic_photo_black_24dp)
                    .into(imageView);
            Log.d("CCC",BuildConfig.BASE_URL + ApiConstants.PLANT_TYPE_IMAGE_ENDPOINT_PREFIX + plantId);
        }
    }


}
