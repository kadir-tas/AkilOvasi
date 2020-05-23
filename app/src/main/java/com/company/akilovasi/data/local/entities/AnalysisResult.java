package com.company.akilovasi.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.company.akilovasi.util.SensorTypes;
import com.google.gson.annotations.SerializedName;

/**
 * This entity is only client side entity. We only kept the data of warning notifications not all analysis results.
 * So, we ignore the True results for this entity. All the "True" and "False" results are stored on the server side.
 */
@Entity(tableName = "analysis_result")
public class AnalysisResult {

    @PrimaryKey
    @SerializedName("resultId")
    private Long resultId;

    @SerializedName("userPlantId")
    private Long userPlantId;

    @SerializedName("version")
    private int version;

    @SerializedName("message")
    private String message;

    @SerializedName("sensorType")
    private SensorTypes sensorType;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public int getVersion() {
        return version;
    }

    public Long getUserPlantId() {
        return userPlantId;
    }

    public void setUserPlantId(Long userPlantId) {
        this.userPlantId = userPlantId;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SensorTypes getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorTypes sensorType) {
        this.sensorType = sensorType;
    }
}
