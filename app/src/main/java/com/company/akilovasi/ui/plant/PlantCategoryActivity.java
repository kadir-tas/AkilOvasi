package com.company.akilovasi.ui.plant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.databinding.ActivityPlantCategoryBinding;
import com.company.akilovasi.ui.BaseActivity;

import java.util.List;

import dagger.android.AndroidInjection;

public class PlantCategoryActivity extends BaseActivity< PlantCategoryViewModel, ActivityPlantCategoryBinding> {



    @Override
    public Class<PlantCategoryViewModel> getViewModel() {
        return PlantCategoryViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_plant_category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        viewModel.getAllBanners().observe(this, new Observer<Resource<List<PlantType>>>() {
            @Override
            public void onChanged(Resource<List<PlantType>> listResource) {
                Log.d("AAA" , listResource.status + " " + listResource.message);
            }
        });

    }
}
