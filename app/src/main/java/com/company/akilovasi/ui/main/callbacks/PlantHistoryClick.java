package com.company.akilovasi.ui.main.callbacks;

import com.company.akilovasi.data.local.entities.PlantHistory;

public interface PlantHistoryClick {
    void onPlantHistoryImageClick(Long plantHistoryId);
    void onPlantHistoryClick(PlantHistory plantHistory);
}
