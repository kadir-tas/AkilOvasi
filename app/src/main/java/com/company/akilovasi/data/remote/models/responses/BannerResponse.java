package com.company.akilovasi.data.remote.models.responses;

import com.company.akilovasi.data.local.entities.Banner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerResponse {

    private List<Banner> results;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Banner> getResults() {
        return results;
    }

    public void setResults(List<Banner> results) {
        this.results = results;
    }
}
