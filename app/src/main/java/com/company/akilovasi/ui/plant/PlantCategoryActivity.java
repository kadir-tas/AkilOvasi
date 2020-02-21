package com.company.akilovasi.ui.plant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.databinding.ActivityPlantCategoryBinding;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.plant.adapters.PlantTypeRecyclerViewAdapter;
import com.company.akilovasi.ui.plant.callbacks.PlantTypeRecyclerViewCallback;

import java.util.List;

import dagger.android.AndroidInjection;

public class PlantCategoryActivity extends BaseActivity< PlantCategoryViewModel, ActivityPlantCategoryBinding> implements PlantTypeRecyclerViewCallback {

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
        //viewModel.getAllPlantTypes().observe(this, listResource -> Log.d("AAA" , listResource.status + " " + listResource.message));

        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerView.setAdapter(new PlantTypeRecyclerViewAdapter(this));

        viewModel.getAllPlantTypes()
                .observe(this, listResource -> dataBinding.setResource(listResource));
    }

    @Override
    public void onPlantTypeClicked(PlantType plantType, View sharedView) {

    }
}
