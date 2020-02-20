package com.company.akilovasi.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

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
}
