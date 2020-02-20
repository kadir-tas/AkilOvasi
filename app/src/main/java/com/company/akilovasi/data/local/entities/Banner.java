package com.company.akilovasi.data.local.entities;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

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

    @BindingAdapter({"imageUrl"})
    public static void loadImageUrl(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.equals("")) {
            Picasso.get()
                    .load(ApiConstants.IMAGE_ENDPOINT_PREFIX)
                    .placeholder(R.drawable.placeholder)
                    .into(view);
            Log.d("CCC",imageUrl);
        }
        Log.d("CCC",imageUrl);
    }
}
