package com.company.akilovasi.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "plantType")
public class PlantType {

    @PrimaryKey
    @SerializedName("plantId")
    private Long plantId;

    @SerializedName("plantImageId")
    private String plantImageId;

    @SerializedName("plantCategory")
    private String plantCategory;

    @SerializedName("plantName")
    private String plantName;

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getPlantImageId() {
        return plantImageId;
    }

    public void setPlantImageId(String plantImageId) {
        this.plantImageId = plantImageId;
    }

    public String getPlantCategory() {
        return plantCategory;
    }

    public void setPlantCategory(String plantCategory) {
        this.plantCategory = plantCategory;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
}
