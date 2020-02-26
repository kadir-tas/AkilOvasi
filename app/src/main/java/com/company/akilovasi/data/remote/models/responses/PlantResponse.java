package com.company.akilovasi.data.remote.models.responses;

import com.company.akilovasi.data.local.entities.Plant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlantResponse {

    private List<Plant> results;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Plant> getResults() {
        return results;
    }

    public void setResults(List<Plant> results) {
        this.results = results;
    }

}
