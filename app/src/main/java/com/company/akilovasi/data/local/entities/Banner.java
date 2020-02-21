package com.company.akilovasi.data.local.entities;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.ApiConstants;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

@Entity(tableName = "banners")
public class Banner {

    @PrimaryKey
    @SerializedName("bannerId")
    private Long bannerId;

    @SerializedName("bannerImageId")
    private String bannerImageId;

    @SerializedName("bannerText")
    private String bannerText;

    @SerializedName("bannerLink")
    private String bannerLink;

    @SerializedName("bannerLinkType")
    private String bannerLinkType;

    @SerializedName("bannerActive")
    private String bannerActive;

    @Nullable
    @SerializedName("imageUrl")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getBannerId() {
        return bannerId;
    }

    public void setBannerId(Long bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerImageId() {
        return bannerImageId;
    }

    public void setBannerImageId(String bannerImageId) {
        this.bannerImageId = bannerImageId;
    }

    public String getBannerText() {
        return bannerText;
    }

    public void setBannerText(String bannerText) {
        this.bannerText = bannerText;
    }

    public String getBannerLink() {
        return bannerLink;
    }

    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink;
    }

    public String getBannerLinkType() {
        return bannerLinkType;
    }

    public void setBannerLinkType(String bannerLinkType) {
        this.bannerLinkType = bannerLinkType;
    }

    public String getBannerActive() {
        return bannerActive;
    }

    public void setBannerActive(String bannerActive) {
        this.bannerActive = bannerActive;
    }

//    @BindingAdapter({"imageUrl"})
//    public static void loadImageUrl(ImageView view, String imageUrl) {
//        Log.d("CCC",imageUrl);
//        if (imageUrl != null && !imageUrl.equals("")) {
//            Picasso.get()
//                    .load(ApiConstants.IMAGE_ENDPOINT_PREFIX)
//                    .placeholder(R.drawable.placeholder)
//                    .into(view);
//            Log.d("CCC",imageUrl);
//        }
//    }

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
