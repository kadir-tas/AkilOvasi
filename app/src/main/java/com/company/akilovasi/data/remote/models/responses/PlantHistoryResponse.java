package com.company.akilovasi.data.remote.models.responses;

import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.local.entities.PlantHistory;

import java.util.List;

public class PlantHistoryResponse {

    private List<PlantHistory> results;

    private String error;

    public PlantHistoryResponse() {
    }

    public PlantHistoryResponse(List<PlantHistory> results, String error) {
        this.results = results;
        this.error = error;
    }

    public List<PlantHistory> getResults() {
        return results;
    }

    public void setResults(List<PlantHistory> results) {
        this.results = results;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
