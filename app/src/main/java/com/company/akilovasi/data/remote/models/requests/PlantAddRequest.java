package com.company.akilovasi.data.remote.models.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlantAddRequest {

    @Expose
    @SerializedName("plantId")
    private Long plantId;

    @Expose
    @SerializedName("userId")
    private Long userId;

    @Expose
    @SerializedName("plantName")
    private String plantName;

    @Expose
    @SerializedName("plantSize")
    private float plantSize;

    @Expose
    @SerializedName("potSize")
    private float potSize;

    public PlantAddRequest(Long plantId, Long userId, String plantName, float plantSize, float potSize) {
        this.plantId = plantId;
        this.userId = userId;
        this.plantName = plantName;
        this.plantSize = plantSize;
        this.potSize = potSize;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public float getPlantSize() {
        return plantSize;
    }

    public void setPlantSize(float plantSize) {
        this.plantSize = plantSize;
    }

    public float getPotSize() {
        return potSize;
    }

    public void setPotSize(float potSize) {
        this.potSize = potSize;
    }
}
