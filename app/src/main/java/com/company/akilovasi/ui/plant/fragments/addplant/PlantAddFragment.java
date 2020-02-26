package com.company.akilovasi.ui.plant.fragments.addplant;

import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.UseCase;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.databinding.FragmentPlantAddBinding;
import com.company.akilovasi.ui.BaseFragment;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantAddFragment extends BaseFragment<PlantAddFragmentViewModel, FragmentPlantAddBinding> implements View.OnClickListener {

    private static final String TAG = "PlantAddFragment";

    private Long plantTypeId;
    private String capturedImagePath = "";

    public static PlantAddFragment newInstance(Long plantTypeId){
        return new PlantAddFragment(plantTypeId);
    }

    public PlantAddFragment(Long plantTypeId) {
        this.plantTypeId = plantTypeId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); /*view model injected*/
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Log.d(TAG, "onCreate: Created");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); /*dataBinder injected*/
        dataBinding.setLoading(true);
        Log.d(TAG, "onViewCreated: Created");
        initObservers();
    }

    private void initObservers() {
        viewModel.getPlantType(plantTypeId).observe(getViewLifecycleOwner(), new Observer<PlantType>() {
            @Override
            public void onChanged(PlantType plantType) {
                if(plantType != null){
                    dataBinding.setPlantType( plantType );
                    dataBinding.addNewPlantButton.setOnClickListener(PlantAddFragment.this);
                    dataBinding.setLoading(false);
                }
            }
        });
    }

    @Override
    public Class<PlantAddFragmentViewModel> getViewModel() {
        return PlantAddFragmentViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_plant_add;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Paused");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Resumed");
    }

    public boolean validateForm(){
        int potSizeId = dataBinding.potSize.getCheckedRadioButtonId();
        int plantSizeId = dataBinding.plantSize.getCheckedRadioButtonId();
        String plantName = dataBinding.plantName.getText().toString();
        return !plantName.equals("") && plantName.length() <= 16 && !(plantSizeId == -1 || potSizeId == -1);
    }

    //TODO: CLEAR THESE TEMP UTIL FUNCTIONS
    //TODO: THERE ARE TEMP FUNCTIONS REMOVE HERE
    //TODO: THESE VALUES MAY NEED MAPPING OF SOME KING
    private float convetToPotSize(int id){

        switch (id){
            case R.id.pot_small: return 1f; /*MADE UP VALUES*/
            case R.id.pot_medium: return 2f; /*MADE UP VALUES*/
            case R.id.pot_big: return 3f; /*MADE UP VALUES*/
            case R.id.pot_huge: return 4f; /*MADE UP VALUES*/
            default: return 0;
        }
    }

    //TODO: CLEAR THESE TEMP UTIL FUNCTIONS
    //TODO: THERE ARE TEMP FUNCTIONS REMOVE HERE
    //TODO: THESE VALUES MAY NEED MAPPING OF SOME KING
    private float convetToPlantSize(int id){

        switch (id){
            case R.id.plant_small: return 5f; /*MADE UP VALUES*/
            case R.id.plant_medium: return 6f; /*MADE UP VALUES*/
            case R.id.plant_big: return 7f; /*MADE UP VALUES*/
            case R.id.plant_huge: return 8f; /*MADE UP VALUES*/
            default: return 0;
        }
    }

    private void saveForm(){
        dataBinding.setLoading(true);
        viewModel.saveUserPlant(
                plantTypeId,
                viewModel.getUserId(),
                dataBinding.plantName.getText().toString(),
                convetToPlantSize( dataBinding.plantSize.getCheckedRadioButtonId() ),
                convetToPotSize( dataBinding.potSize.getCheckedRadioButtonId() )
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: " + response.message());
                Toast.makeText(getContext(), R.string.plant_add, Toast.LENGTH_SHORT).show();
                //TODO: TEMP CODE REMOVE ME
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                dataBinding.setLoading(false);
            }
        });
    }

    private void saveImage(){
        viewModel.saveUserPlantImage( capturedImagePath, plantTypeId , viewModel.getUserId());
    }

    private void foo(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addNewPlantButton:
                if(validateForm()){
                    saveForm();
                }else {
                    Toast.makeText(getContext(), R.string.form_fill_alert, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.takePictureButton:
                break;
        }
    }
}
