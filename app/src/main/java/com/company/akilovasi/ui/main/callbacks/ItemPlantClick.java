package com.company.akilovasi.ui.main.callbacks;

import com.company.akilovasi.data.local.entities.Plant;

public interface ItemPlantClick {
    void onPlantClick(Plant plant, int position);
    void onPlantImageClick(Long userPlantId);
}
