package com.company.akilovasi.data.remote.models.responses;

import com.company.akilovasi.data.local.entities.PlantType;

import java.util.List;

public class PlantTypeResponse {
    private List<PlantType> results;

    public List<PlantType> getResults() {
        return results;
    }

    public void setResults(List<PlantType> results) {
        this.results = results;
    }
}
