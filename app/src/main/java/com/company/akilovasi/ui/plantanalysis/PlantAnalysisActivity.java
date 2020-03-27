package com.company.akilovasi.ui.plantanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.company.akilovasi.R;
import com.company.akilovasi.databinding.ActivityPlantAnalysisBinding;
import com.company.akilovasi.ui.BaseActivity;

public class PlantAnalysisActivity extends BaseActivity<PlantAnalysisViewModel, ActivityPlantAnalysisBinding> {

    @Override
    public Class<PlantAnalysisViewModel> getViewModel() {
        return PlantAnalysisViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_plant_analysis;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
