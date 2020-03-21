package com.company.akilovasi.ui.plant.fragments.addplant;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.company.akilovasi.data.remote.models.responses.Response;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingComponent;
import androidx.lifecycle.Observer;
import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.databinding.FragmentPlantAddBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseFragment;
import com.company.akilovasi.ui.camera.CameraActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;


public class PlantAddFragment extends BaseFragment<PlantAddFragmentViewModel, FragmentPlantAddBinding> implements View.OnClickListener {

    private static final String TAG = "PlantAddFragment";

    private static final int REQUEST_CODE = 1;

    private Long plantTypeId;
    private String capturedImagePath = "";

    @Inject
    Picasso picasso;

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
                    dataBinding.takePictureButton.setOnClickListener(PlantAddFragment.this);
                    dataBinding.setLoading(false);
                }
            }
        });
    }

/*
    private void dispatchTakePictureIntent(){
        //startActivityForResult(new Intent(getActivity(), CameraActivity.class),REQUEST_CODE);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }

    private void dispatchFullSizeTakePicIntent(){

        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
        Uri photoURI = FileProvider.getUriForFile(getContext(),
                "com.example.android.fileprovider",
                file);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, REQUEST_CODE);
    }
*/

    private void dispatchInAppImageTakeIntent(){
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + REQUEST_CODE + " , " + resultCode);
        if( requestCode == REQUEST_CODE) {
           /* Bitmap photo = (Bitmap) data.getExtras().get("data");
            dataBinding.capturedPlantImage.setImageBitmap(photo);*/
          /* capturedImagePath = data.getStringExtra("path");
            Toast.makeText(getContext(), "Saved at " + capturedImagePath, Toast.LENGTH_SHORT).show();
            final int THUMBSIZE = 128;
            Bitmap bitmap =

            dataBinding.capturedPlantImage.setImageBitmap(thumbImage);
            dataBinding.capturedPlantImage.setRotation(90);*/

       /*   if(data != null){
              Log.d(TAG, "onActivityResult: Data not null");
              Bundle extras = data.getExtras();
              if(extras != null){
                  Log.d(TAG, "onActivityResult: extra not null");
                  Bitmap imageBitmap = (Bitmap) extras.get("data");
                  if(imageBitmap != null) {
                      Log.d(TAG, "onActivityResult: bitmap not null");
                      dataBinding.capturedPlantImage.setImageBitmap(imageBitmap);
                      //Uri tempUri = getImageUri(getContext(), imageBitmap);
                      //capturedImagePath = getRealPathFromURI(tempUri);
                      Log.d(TAG, "onActivityResult: "  + capturedImagePath);
                  }
              }
          }else {
              Log.d(TAG, "onActivityResult: Data is null");
          }
          */

            Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(capturedImagePath),
                    64, 64);
            dataBinding.capturedPlantImage.setImageBitmap(bitmap);

        }else{
            dataBinding.capturedPlantImage.setImageBitmap(null);
            capturedImagePath = "";
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity( getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.akilovasi.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        capturedImagePath = image.getAbsolutePath();
        return image;
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
    public DataBindingComponent getBindigComponent() {
        return new BindingComponent(picasso);
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
        ).enqueue(new Callback<Response<Message>>() {
            @Override
            public void onResponse(Call<Response<Message>> call, retrofit2.Response<Response<Message>> response) {
                Response<Message> message = response.body();
                if(response.isSuccessful() && message != null){
                    Log.d(TAG, "onResponse: " + message.getSuccess());
                    plantAdded();
                    getActivity().onBackPressed();
                }else{
                    loadingFailed();
                }
            }

            @Override
            public void onFailure(Call<Response<Message>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                loadingFailed();
            }
        });
    }


    private void saveImage(){
        dataBinding.setLoading(true);
        viewModel.saveUserPlantWithImage(
                plantTypeId,
                viewModel.getUserId(),
                dataBinding.plantName.getText().toString(),
                convetToPlantSize( dataBinding.plantSize.getCheckedRadioButtonId() ),
                convetToPotSize( dataBinding.potSize.getCheckedRadioButtonId() ),
                capturedImagePath
        ).enqueue(new Callback<Response<Message>>() {
            @Override
            public void onResponse(Call<Response<Message>> call, retrofit2.Response<Response<Message>> response) {
                Response<Message> message = response.body();
                if(response.isSuccessful() && message != null){
                    plantAdded();
                    getActivity().onBackPressed();
                }else{
                    Log.d(TAG, "onResponse: HERE");
                   loadingFailed();
                }
            }

            @Override
            public void onFailure(Call<Response<Message>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                loadingFailed();
            }
        });
    }

    private void showLoading(){

    }

    private void loadingFailed(){
        Toast.makeText(getActivity(), R.string.plant_add_failed, Toast.LENGTH_SHORT).show();
        dataBinding.setLoading(false);
    }

    private void plantAdded(){
        Toast.makeText(getActivity(), R.string.plant_add, Toast.LENGTH_SHORT).show();
        dataBinding.setLoading(false);
    }

    private void loadingSuccsess(){

    }

    private void hideLoading(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addNewPlantButton:
                if(validateForm()){
                    if(capturedImagePath.equals("")){
                        saveForm();
                    }else{
                        saveImage();
                    }
                }else {
                    Toast.makeText(getContext(), R.string.form_fill_alert, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.takePictureButton:
                //dispatchInAppImageTakeIntent();
                dispatchTakePictureIntent();
                Log.d(TAG, "onClick: takePicutre");
                break;
        }
    }
}
