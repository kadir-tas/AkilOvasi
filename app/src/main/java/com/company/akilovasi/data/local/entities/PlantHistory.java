package com.company.akilovasi.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(tableName = "plant_history")
public class PlantHistory {

    @PrimaryKey
    @SerializedName("id")
    private Long id;

    @SerializedName("plantTypeId")
    private Long plantTypeId;

    @SerializedName("userPlantId")
    private Long userPlantId;

    @SerializedName("userPlantName")
    private String userPlantName;

    @SerializedName("plantCategory")
    private String plantCategory;

    @SerializedName("userId")
    private Long userId;

    @SerializedName("dateSubmited")
    private Date dateSubmited;

    @SerializedName("plantImageId")
    private String plantImageId;

    @SerializedName("ph")
    @NotNull
    private Long ph;

    @SerializedName("temp")
    @NotNull
    private Long temp;

    @SerializedName("light")
    @NotNull
    private Long light;

    @SerializedName("humiditiy")
    @NotNull
    private Long humiditiy;

    @SerializedName("pageId")
    @NotNull
    private int pageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantTypeId() {
        return plantTypeId;
    }

    public void setPlantTypeId(Long plantTypeId) {
        this.plantTypeId = plantTypeId;
    }

    public Long getUserPlantId() {
        return userPlantId;
    }

    public void setUserPlantId(Long userPlantId) {
        this.userPlantId = userPlantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDateSubmited() {
        return dateSubmited;
    }

    public void setDateSubmited(Date dateSubmited) {
        this.dateSubmited = dateSubmited;
    }

    public String getPlantImageId() {
        return plantImageId;
    }

    public void setPlantImageId(String plantImageId) {
        this.plantImageId = plantImageId;
    }

    @NotNull
    public Long getPh() {
        return ph;
    }

    public void setPh(@NotNull Long ph) {
        this.ph = ph;
    }

    @NotNull
    public Long getTemp() {
        return temp;
    }

    public void setTemp(@NotNull Long temp) {
        this.temp = temp;
    }

    @NotNull
    public Long getLight() {
        return light;
    }

    public void setLight(@NotNull Long light) {
        this.light = light;
    }

    @NotNull
    public Long getHumiditiy() {
        return humiditiy;
    }

    public void setHumiditiy(@NotNull Long humiditiy) {
        this.humiditiy = humiditiy;
    }

    public String getUserPlantName() {
        return userPlantName;
    }

    public void setUserPlantName(String userPlantName) {
        this.userPlantName = userPlantName;
    }

    public String getPlantCategory() {
        return plantCategory;
    }

    public void setPlantCategory(String plantCategory) {
        this.plantCategory = plantCategory;
    }

    @NotNull
    public int getPageId() {
        return pageId;
    }

    public void setPageId(@NotNull int pageId) {
        this.pageId = pageId;
    }
}
