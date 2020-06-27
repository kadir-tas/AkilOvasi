package com.company.akilovasi.data.local.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "blog")
public class Blog {

    @PrimaryKey
    @SerializedName("blogId")
    private Long blogId;

    @SerializedName("blogText")
    private String blogText;

    @SerializedName("blogHeader")
    private String blogHeader;

    @SerializedName("writtenBy")
    private String writtenBy;

    @SerializedName("lastEditedBy")
    private String lastEditedBy;

    @SerializedName("date")
    private Date date;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getBlogText() {
        return blogText;
    }

    public void setBlogText(String blogText) {
        this.blogText = blogText;
    }

    public String getBlogHeader() {
        return blogHeader;
    }

    public void setBlogHeader(String blogHeader) {
        this.blogHeader = blogHeader;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Blog(Long blogId, String blogText, String blogHeader, String writtenBy, String lastEditedBy, Date date) {
        this.blogId = blogId;
        this.blogText = blogText;
        this.blogHeader = blogHeader;
        this.writtenBy = writtenBy;
        this.lastEditedBy = lastEditedBy;
        this.date = date;
    }
}
