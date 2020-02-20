package com.company.akilovasi.data.remote.models.responses;

import com.company.akilovasi.data.local.entities.Banner;

import java.util.List;

public class BannerResponse {

    private List<Banner> results;

    public List<Banner> getResults() {
        return results;
    }

    public void setResults(List<Banner> results) {
        this.results = results;
    }
}
