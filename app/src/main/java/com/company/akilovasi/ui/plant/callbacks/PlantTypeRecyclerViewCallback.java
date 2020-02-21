package com.company.akilovasi.ui.plant.callbacks;

import android.view.View;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.local.entities.PlantType;

public interface PlantTypeRecyclerViewCallback {

    void onPlantTypeClicked(PlantType plantType, View sharedView);


}
