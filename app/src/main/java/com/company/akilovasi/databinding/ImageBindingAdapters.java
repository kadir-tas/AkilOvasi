//package com.company.akilovasi.databinding;
//
//import android.util.Log;
//import android.widget.ImageView;
//
//import androidx.databinding.BindingAdapter;
//
//import com.company.akilovasi.BuildConfig;
//import com.company.akilovasi.R;
//import com.company.akilovasi.data.remote.ApiConstants;
//import com.squareup.picasso.Callback;
//import com.squareup.picasso.NetworkPolicy;
//import com.squareup.picasso.Picasso;
//
//public class ImageBindingAdapters {
//
//    @BindingAdapter({ "bannerImageUrl" })
//    public void loadImage(ImageView imageView, String imageUrl) {
//
//        if (imageUrl != null && !imageUrl.equals("")) {
//            Picasso.get()
//                    .load(BuildConfig.BASE_URL + ApiConstants.BANNER_IMAGE_ENDPOINT_PREFIX+imageUrl)
//                    .networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
//                @Override
//                public void onSuccess() {
//                    Log.d("CACHEEE","IMAGE FROM CACHE");
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    Picasso.get()
//                            .load(BuildConfig.BASE_URL + ApiConstants.BANNER_IMAGE_ENDPOINT_PREFIX+imageUrl)
//                            .placeholder(R.drawable.placeholder)
//                            .into(imageView);
//                    Log.d("CACHEEE","IMAGE IS NOT FROM CACHE");
//
//
//                }
//            });
//
//            Log.d("CCC",BuildConfig.BASE_URL + ApiConstants.BANNER_IMAGE_ENDPOINT_PREFIX+imageUrl);
//        }
//    }
//
//
//    @BindingAdapter({ "imageUrl" })
//    public void loadImagePlantType(ImageView imageView, Long plantId) {
//
//        if (plantId != null) {
//            Picasso.get()
//                    .load(BuildConfig.BASE_URL + ApiConstants.PLANT_TYPE_IMAGE_ENDPOINT_PREFIX + plantId)
//                    .placeholder(R.drawable.placeholder)
//                    .into(imageView);
//            Log.d("CCC",BuildConfig.BASE_URL + ApiConstants.PLANT_TYPE_IMAGE_ENDPOINT_PREFIX + plantId);
//        }
//    }
//}