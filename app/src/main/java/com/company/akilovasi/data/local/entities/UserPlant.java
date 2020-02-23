package com.company.akilovasi.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "userPlants")
public class UserPlant {

    @SerializedName("user_id")
    private Long user_id;

    @PrimaryKey
    @SerializedName("userPlantId")
    private Long userPlantId;

    @SerializedName("plantType_id")
    private String plantType_id;

    @SerializedName("plantSize")
    private float plantSize;

    @SerializedName("plantPotSize")
    private float plantPotSize;

    @SerializedName("plantName")
    private String plantName;

    @SerializedName("userPlantImageId")
    private  String userPlantImageId;

    @SerializedName("lastSensPh")
    private float lastSensPh;

    @SerializedName("lastSensTemp")
    private float lastSensTemp;

    @SerializedName("lastSensHumidity")
    private float lastSensHumidity;

    @SerializedName("lastSensLight")
    private float lastSensLight;

    public UserPlant(Long user_id, Long userPlantId, String plantType_id, float plantSize, float plantPotSize, String plantName, String userPlantImageId, float lastSensPh, float lastSensTemp, float lastSensHumidity, float lastSensLight) {
        this.user_id = user_id;
        this.userPlantId = userPlantId;
        this.plantType_id = plantType_id;
        this.plantSize = plantSize;
        this.plantPotSize = plantPotSize;
        this.plantName = plantName;
        this.userPlantImageId = userPlantImageId;
        this.lastSensPh = lastSensPh;
        this.lastSensTemp = lastSensTemp;
        this.lastSensHumidity = lastSensHumidity;
        this.lastSensLight = lastSensLight;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getUserPlantId() {
        return userPlantId;
    }

    public void setUserPlantId(Long userPlantId) {
        this.userPlantId = userPlantId;
    }

    public String getPlantType_id() {
        return plantType_id;
    }

    public void setPlantType_id(String plantType_id) {
        this.plantType_id = plantType_id;
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

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getUserPlantImageId() {
        return userPlantImageId;
    }

    public void setUserPlantImageId(String userPlantImageId) {
        this.userPlantImageId = userPlantImageId;
    }

    public float getLastSensPh() {
        return lastSensPh;
    }

    public void setLastSensPh(float lastSensPh) {
        this.lastSensPh = lastSensPh;
    }

    public float getLastSensTemp() {
        return lastSensTemp;
    }

    public void setLastSensTemp(float lastSensTemp) {
        this.lastSensTemp = lastSensTemp;
    }

    public float getLastSensHumidity() {
        return lastSensHumidity;
    }

    public void setLastSensHumidity(float lastSensHumidity) {
        this.lastSensHumidity = lastSensHumidity;
    }

    public float getLastSensLight() {
        return lastSensLight;
    }

    public void setLastSensLight(float lastSensLight) {
        this.lastSensLight = lastSensLight;
    }
}
