package com.company.akilovasi.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "plant_history")
public class PlantHistory implements Serializable {

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlantTypeId(Long plantTypeId) {
        this.plantTypeId = plantTypeId;
    }

    public void setUserPlantId(Long userPlantId) {
        this.userPlantId = userPlantId;
    }

    public void setUserPlantName(String userPlantName) {
        this.userPlantName = userPlantName;
    }

    public void setPlantCategory(String plantCategory) {
        this.plantCategory = plantCategory;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDateSubmited(Date dateSubmited) {
        this.dateSubmited = dateSubmited;
    }

    public void setPlantImageId(String plantImageId) {
        this.plantImageId = plantImageId;
    }

    public void setPh(@NotNull Long ph) {
        this.ph = ph;
    }

    public void setTemp(@NotNull Long temp) {
        this.temp = temp;
    }

    public void setLight(@NotNull Long light) {
        this.light = light;
    }

    public void setHumiditiy(@NotNull Long humiditiy) {
        this.humiditiy = humiditiy;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public Long getId() {
        return id;
    }

    public Long getPlantTypeId() {
        return plantTypeId;
    }

    public Long getUserPlantId() {
        return userPlantId;
    }

    public String getUserPlantName() {
        return userPlantName;
    }

    public String getPlantCategory() {
        return plantCategory;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getDateSubmited() {
        return dateSubmited;
    }

    public String getPlantImageId() {
        return plantImageId;
    }

    @NotNull
    public Long getPh() {
        return ph;
    }

    @NotNull
    public Long getTemp() {
        return temp;
    }

    @NotNull
    public Long getLight() {
        return light;
    }

    @NotNull
    public Long getHumiditiy() {
        return humiditiy;
    }

    public int getPageId() {
        return pageId;
    }
}
