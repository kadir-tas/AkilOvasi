package com.company.akilovasi.data.local.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


@Entity(tableName = "notifications")
public class Notification {

    /**
     * Notification Types
     */
    public enum Type{
        /**
         * For Basic Notifications Only Contains Text
         */
        Default,
        /**
         * To Remind User To Do Analysis For his/her
         */
        RemindAnalysis,
        /**
         *Remind User to Take Care of His/Her Plant, Give Water etc..
         */
        RemindCare,
        /**
         * Notifications such as go to external links when clicked or show something on the screen
         */
        GeneralNotification
    }

    @PrimaryKey
    @SerializedName("id")
    private Long id;

    /**
     * Version 1 means that the extra data will be True and False string representation.
     * In other words, a string like TFTT is expected to represent the data of
     * soilTemperature, soilHumidty, airTemperature, airHumidity, airPressure, environmentLight.
     */
    @SerializedName("version")
    private int version ;

    @SerializedName("message")
    private String message;

    @SerializedName("type")
    private Notification.Type type;

    @SerializedName("date")
    private Date date;

    @SerializedName("userPlantId")
    private Long userPlantId;

    @SerializedName("externalLink")
    private String externalLink;

    @SerializedName("extra")
    private String extra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Long getUserPlantId() {
        return userPlantId;
    }

    public void setUserPlantId(Long userPlantId) {
        this.userPlantId = userPlantId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
