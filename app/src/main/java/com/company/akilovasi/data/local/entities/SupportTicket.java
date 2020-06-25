package com.company.akilovasi.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "supportTicket")
public class SupportTicket {

    @PrimaryKey
    @SerializedName("id")
    private Long id;

    @SerializedName("userId")
    private Long userId;

    @SerializedName("userPlantId")
    private Long userPlantId;

    @SerializedName("subject")
    private String subject;

    @SerializedName("description")
    private String description;

    //TODO: Status needs to be stored as enum rather then string
    @SerializedName("description")
    private String status;

    public SupportTicket() {
    }

    public SupportTicket(Long id, Long userId, Long userPlantId, String subject, String description, String status) {
        this.id = id;
        this.userId = userId;
        this.userPlantId = userPlantId;
        this.subject = subject;
        this.description = description;
        this.status = status;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
