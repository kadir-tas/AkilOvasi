package com.company.akilovasi.ui.plant;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.databinding.ActivityPlantCategoryBinding;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.camera.CameraActivity;
import com.company.akilovasi.ui.main.MainActivity;
import com.company.akilovasi.ui.plant.callbacks.ItemPlantTypeClick;
import com.company.akilovasi.ui.plant.fragments.addplant.PlantAddFragment;
import com.company.akilovasi.ui.plant.fragments.plantcategory.PlantCategoryFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import dagger.android.AndroidInjection;

public class PlantCategoryActivity extends BaseActivity<PlantCategoryActivityViewModel, ActivityPlantCategoryBinding> implements ItemPlantTypeClick {
    private static final String TAG = "PlantCategoryActivity";

    @Override
    public Class<PlantCategoryActivityViewModel> getViewModel() {
        return PlantCategoryActivityViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_plant_category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Created");
        initObservers();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},  1);
        dataBinding.goBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected DataBindingComponent getDataBindingComponent() {
        return null;
    }

    private void initObservers(){
        Log.d(TAG, "initObservers: Observers initilizing...");
        viewModel.getAllPlantTypes().observe(this, new Observer<Resource<List<PlantType>>>() {
            @Override
            public void onChanged(Resource<List<PlantType>> listResource) {
                if(listResource != null){
                    switch (listResource.status){
                        case SUCCESS:
                            if(listResource.data != null){
                                setAdapterWithPlantTypeList();
                                Log.d(TAG, "onChanged: PlantTypes are loaded");
                            }else {
                                Log.e(TAG, "onChanged: Error list listResource.data  is null");
                            }
                            dataBinding.progressBar.hide();
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: Error" + listResource.message);
                            Toast.makeText(PlantCategoryActivity.this, R.string.no_connection, Toast.LENGTH_LONG).show();
                            dataBinding.progressBar.hide();
                            break;
                        case LOADING:
                            Log.d(TAG, "onChanged: Loading...");
                            dataBinding.progressBar.show();
                            break;
                    }
                }
            }
        });
    }

    public void setAdapterWithPlantTypeList(){
        Log.d(TAG, "setAdapterWithPlantTypeList: setting up adapter with plant type list");
        viewModel.getListOfPlantCategories().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if(strings != null){
                    List<PlantCategoryFragment> plantCategoryFragments = new ArrayList<>(); /*List of fragments to be supplied to the ViewPager Adapter*/

                    for(String s: strings){
                        Log.d(TAG, "onChanged: creating PlantCategoryFragment of type " + s);
                        plantCategoryFragments.add( PlantCategoryFragment.newInstance( s )); /*Create A fragment instance based on the plant category, fragment will handle the data retrieving by using PlantTypeRepo*/
                    }

                    PlantCategoryPagerAdapter plantCategoryPagerAdapter = new PlantCategoryPagerAdapter(getSupportFragmentManager());
                    plantCategoryPagerAdapter.setPlantCategoryFragments( plantCategoryFragments );
                    dataBinding.setPlantCategoryPagerAdapter(plantCategoryPagerAdapter);
                    viewModel.getListOfPlantCategories().removeObserver(this);

                    if(plantCategoryFragments.size() > 4)
                        dataBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                }
            }
        });
    }

    @Override
    public void onClick(PlantType plantType) {
        //TEMP WAY TO SPAWN PLANT ADD FRAGMENT, FOR TESTING PURPOSES
        Log.d(TAG, "onClick: Plant clicked " + plantType.getPlantId());
        Fragment f = getSupportFragmentManager().findFragmentByTag("plant_add_fragment");
        if(f == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PlantAddFragment.newInstance(plantType.getPlantId()), "plant_add_fragment").commit();
        }
    }

    @Override
    public void onBackPressed() {
        //TEMP WAY TO HANDLE BACK PRESS, FOR TESTING PURPOSES
        Fragment f = getSupportFragmentManager().findFragmentByTag("plant_add_fragment");
        if(f != null){
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }else
        {
            startActivity(new Intent( PlantCategoryActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
