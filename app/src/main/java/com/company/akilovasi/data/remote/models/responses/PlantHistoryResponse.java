package com.company.akilovasi.data.remote.models.responses;

import com.company.akilovasi.data.local.entities.Banner;

import java.util.List;

public class PlantHistoryResponse {

    private List<Banner> results;

    private String error;

    public PlantHistoryResponse() {
    }

    public PlantHistoryResponse(List<Banner> results, String error) {
        this.results = results;
        this.error = error;
    }

    public List<Banner> getResults() {
        return results;
    }

    public void setResults(List<Banner> results) {
        this.results = results;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
