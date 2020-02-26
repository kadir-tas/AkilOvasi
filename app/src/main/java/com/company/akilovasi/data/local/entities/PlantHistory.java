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

    @Embedded(prefix = "plant_history_plantType_")
    @SerializedName("plant_type")
    private PlantType plantType;

    @Embedded(prefix = "plant_history_plant_")
    @SerializedName("plant")
    private Plant plant;

    @Embedded(prefix = "plant_history_user_")
    @SerializedName("user")
    private User user;

    @SerializedName("darte_submitted")
    private Date dateSubmited;

    @SerializedName("plant_image_id")
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

    @SerializedName("humidity")
    @NotNull
    private Long humiditiy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
