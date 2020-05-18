package com.company.akilovasi.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "analysis_result")
public class AnalysisResult {

    @PrimaryKey
    @SerializedName("resultId")
    private Long resultId;

    @SerializedName("version")
    private int version;

    @SerializedName("soilTemperatureStatus")
    private boolean soilTemperatureStatus;

    @SerializedName("soilHumidtyStatus")
    private boolean soilHumidtyStatus;

    @SerializedName("airTemperatureStatus")
    private boolean airTemperatureStatus;

    @SerializedName("airHumidityStatus")
    private boolean airHumidityStatus;

    @SerializedName("airPressureStatus")
    private boolean airPressureStatus;

    @SerializedName("environmentLightStatus")
    private boolean environmentLightStatus;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isSoilTemperatureStatus() {
        return soilTemperatureStatus;
    }

    public void setSoilTemperatureStatus(boolean soilTemperatureStatus) {
        this.soilTemperatureStatus = soilTemperatureStatus;
    }

    public boolean isSoilHumidtyStatus() {
        return soilHumidtyStatus;
    }

    public void setSoilHumidtyStatus(boolean soilHumidtyStatus) {
        this.soilHumidtyStatus = soilHumidtyStatus;
    }

    public boolean isAirTemperatureStatus() {
        return airTemperatureStatus;
    }

    public void setAirTemperatureStatus(boolean airTemperatureStatus) {
        this.airTemperatureStatus = airTemperatureStatus;
    }

    public boolean isAirHumidityStatus() {
        return airHumidityStatus;
    }

    public void setAirHumidityStatus(boolean airHumidityStatus) {
        this.airHumidityStatus = airHumidityStatus;
    }

    public boolean isAirPressureStatus() {
        return airPressureStatus;
    }

    public void setAirPressureStatus(boolean airPressureStatus) {
        this.airPressureStatus = airPressureStatus;
    }

    public boolean isEnvironmentLightStatus() {
        return environmentLightStatus;
    }

    public void setEnvironmentLightStatus(boolean environmentLightStatus) {
        this.environmentLightStatus = environmentLightStatus;
    }
}
