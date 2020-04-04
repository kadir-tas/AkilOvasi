package com.company.akilovasi.data.remote.models.requests;

import okhttp3.MultipartBody;
import retrofit2.http.Part;

//NOTE: THIS CLASS IS NOT USE TO SEND DATA TO SERVER IT IS JUST CONVENIENT TO USE TRANSFER DATA
public class PlantValueUpdateRequest {
    private Long userId;
    private Long userPlantId;

    private String imagePath;

    private Long sensPh;
    private Long sensTemp;
    private Long sensHumidity;
    private Long sensLight;
    private float plantSize;
    private float plantPotSize;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserPlantId() {
        return userPlantId;
    }

    public void setUserPlantId(Long userPlantId) {
        this.userPlantId = userPlantId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getSensPh() {
        return sensPh;
    }

    public void setSensPh(Long sensPh) {
        this.sensPh = sensPh;
    }

    public Long getSensTemp() {
        return sensTemp;
    }

    public void setSensTemp(Long sensTemp) {
        this.sensTemp = sensTemp;
    }

    public Long getSensHumidity() {
        return sensHumidity;
    }

    public void setSensHumidity(Long sensHumidity) {
        this.sensHumidity = sensHumidity;
    }

    public Long getSensLight() {
        return sensLight;
    }

    public void setSensLight(Long sensLight) {
        this.sensLight = sensLight;
    }

    public float getPlantSize() {
        return plantSize;
    }

    public void setPlantSize(float plantSize) {
        this.plantSize = plantSize;
    }

    public float getPlantPotSize() {
        return plantPotSize;
    }

    public void setPlantPotSize(float plantPotSize) {
        this.plantPotSize = plantPotSize;
    }
}
