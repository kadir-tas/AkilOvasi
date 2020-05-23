package com.company.akilovasi.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "plants",indices = {@Index(value = {"userId", "userPlantId"},
        unique = true)})
public class Plant implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private Long id;

    @SerializedName("userId")
    private Long userId;

    @SerializedName("userPlantId")
    private Long userPlantId;

    @SerializedName("userPlantName")
    private String userPlantName;

    @Embedded(prefix = "plant_plantType_")
    @SerializedName("plantType")
    private PlantType plantType;

    @SerializedName("userPlantImageId")
    private String userPlantImageId;

    @SerializedName("lastSensPh")
    private float lastSensPh;

    @SerializedName("lastSensTemp")
    private float lastSensTemp;

    @SerializedName("lastSensHumidity")
    private float lastSensHumidity;

    @SerializedName("lastSensLight")
    private float lastSensLight;

    @SerializedName("lastAnalysisDate")
    private Date lastAnalysisDate;

    public Date getLastAnalysisDate() {
        return lastAnalysisDate;
    }

    public void setLastAnalysisDate(Date lastAnalysisDate) {
        this.lastAnalysisDate = lastAnalysisDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getUserPlantName() {
        return userPlantName;
    }

    public void setUserPlantName(String userPlantName) {
        this.userPlantName = userPlantName;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
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
