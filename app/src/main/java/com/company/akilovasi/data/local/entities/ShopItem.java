package com.company.akilovasi.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "shopItems")
public class ShopItem {

    @PrimaryKey
    @SerializedName("id")
    private Long id;

    @SerializedName("itemName")
    private String itemName;

    @SerializedName("category")
    private String category;

    @SerializedName("subCategory")
    private String subCategory;

    @SerializedName("linkType")
    private String linkType;

    @SerializedName("link")
    private String link;

    public ShopItem(Long id, String itemName, String category, String subCategory, String linkType, String link) {
        this.id = id;
        this.itemName = itemName;
        this.category = category;
        this.subCategory = subCategory;
        this.linkType = linkType;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
