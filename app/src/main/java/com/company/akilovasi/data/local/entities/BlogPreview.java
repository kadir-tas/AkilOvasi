package com.company.akilovasi.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "blogPreview")
public class BlogPreview {

    @PrimaryKey
    @SerializedName("blogId")
    private Long blogId;

    @SerializedName("pageId")
    private int pageId;

    @SerializedName("header")
    private String header;

    @SerializedName("previewText")
    private String previewText;

    @SerializedName("writtenBy")
    private String writtenBy;

    @SerializedName("date")
    private Date date;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public BlogPreview(Long blogId, int pageId, String header, String previewText, String writtenBy) {
        this.blogId = blogId;
        this.pageId = pageId;
        this.header = header;
        this.previewText = previewText;
        this.writtenBy = writtenBy;
    }
}
