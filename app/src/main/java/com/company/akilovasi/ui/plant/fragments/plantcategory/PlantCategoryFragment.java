package com.company.akilovasi.ui.plant.fragments.plantcategory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.databinding.PlantCategoryFragmentBinding;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.camera.CameraActivity;
import com.company.akilovasi.ui.main.MainActivity;
import com.company.akilovasi.ui.plant.adapters.PlantCategoryAdapter;
import com.company.akilovasi.ui.plant.callbacks.ItemPlantTypeClick;

import java.util.List;

import javax.inject.Inject;

public class PlantCategoryFragment extends BaseFragment<PlantCategoryFragmentViewModel, PlantCategoryFragmentBinding> {
    private static final String TAG = "PlantCategoryFragment";


    @Inject
    public PlantCategoryAdapter adapter;

    private String plantCategory;

    private ItemPlantTypeClick itemPlantTypeClick;

    public static PlantCategoryFragment newInstance(String plantCategory) {
        return new PlantCategoryFragment(plantCategory);
    }

    private PlantCategoryFragment(String plantCategory){
        Log.d(TAG + plantCategory, "PlantCategoryFragment: Constructed with " + plantCategory + " type of plants");
        this.plantCategory = plantCategory;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setPlantCategory(plantCategory);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initObservers(){
        viewModel.getPlants().observe(getViewLifecycleOwner(), new Observer<List<PlantType>>() {
            @Override
            public void onChanged(List<PlantType> plantTypes) {
                Log.d(TAG + plantCategory, "onChanged: Called");
                if(plantTypes != null){
                    Log.d(TAG + plantCategory, "onChanged: Plant " + plantCategory + " Types Recevied");

                    adapter.setData( plantTypes );
                    adapter.notifyDataSetChanged();

                    dataBinding.progressBar.hide();

                }else{
                    Log.d(TAG + plantCategory, "onChanged: Plant " + plantCategory + " Types are not Recevied");
                    dataBinding.progressBar.show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG + plantCategory, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG + plantCategory, "onPause: ");
    }

    @Override
    public Class<PlantCategoryFragmentViewModel> getViewModel() {
        return PlantCategoryFragmentViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.plant_category_fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initObservers();
        dataBinding.setPlantCategoryAdapter(adapter);
        adapter.setItemPlantTypeClick(itemPlantTypeClick);
        dataBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return dataBinding.getRoot();
    }

    @NonNull
    @Override
    public String toString() {
        return plantCategory;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ItemPlantTypeClick){
            itemPlantTypeClick = (ItemPlantTypeClick)context;
        }else{
            Log.e(TAG, "onAttach: " + context.toString() + " needs to have ItemPlantTypeClick method implemented");
        }
    }
}
