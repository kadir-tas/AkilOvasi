package com.company.akilovasi.ui.plantanalysis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingComponent;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.company.akilovasi.R;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.PlantValueUpdateRequest;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.databinding.ActivityPlantAnalysisBinding;
import com.company.akilovasi.databinding.components.BindingComponent;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.main.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;

public class PlantAnalysisActivity extends BaseActivity<PlantAnalysisViewModel, ActivityPlantAnalysisBinding> implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private static final String TAG = "PlantAnalysisActivity";
    public static String PARAM_USER_PLANT = "userPlantId";

    private Long userPlantId;
    private String capturedImagePath = "";

    @Inject
    public Picasso picasso;

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
        dataBinding.setLoading(true);
        userPlantId = -1L;
        
        if(getIntent().hasExtra(PARAM_USER_PLANT)){
            String userPlantIdStr = getIntent().getStringExtra(PARAM_USER_PLANT);
            if(userPlantIdStr != null){
                userPlantId =  Long.parseLong( userPlantIdStr );
            }else{
                userPlantId = getIntent().getLongExtra(PARAM_USER_PLANT , -1);
            }
        }else {
            Log.e(TAG, "onCreate: intent has no  " + PARAM_USER_PLANT + " parameter" );
        }

        if(userPlantId == -1L){
            Log.e(TAG, "onCreate: UserPlant is null");
            startActivity(new Intent(PlantAnalysisActivity.this, MainActivity.class));
            finish();
        }

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},  1);

        //If user enters this activity directly recent notifications wont be polled thus when user completes analysis action related notification wont be removed
        //So poll them here
        viewModel.pollNotifications();
        initObservers();
    }

    @Override
    protected DataBindingComponent getDataBindingComponent() {
        return new BindingComponent(picasso);
    }


    private void initObservers() {

        Log.d(TAG, "initObservers: Getting " + userPlantId);
        viewModel.getUserPlantLocal(userPlantId).observe(this,userPlant -> {
            if(userPlant != null){
                dataBinding.setPlant(userPlant);
                Log.d(TAG, "initObservers: plant type name = " + userPlant.getPlantType().getPlantName());
                dataBinding.setPlant(userPlant);
                dataBinding.setLoading(false);
                dataBinding.updateSensValueButton.setOnClickListener(this);
                dataBinding.takePictureButton.setOnClickListener(this);
            }else{
                Log.d(TAG, "initObservers: plant error");
                Toast.makeText(this, R.string.common_err, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length == 2){
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, R.string.error_perm, Toast.LENGTH_SHORT).show();
                    redirectToMain();
                }
            }
        }
    }

    public void saveForm(){
        PlantValueUpdateRequest req = new PlantValueUpdateRequest();

        req.setUserId( viewModel.getUserId() );
        req.setImagePath( capturedImagePath );
        req.setUserPlantId( userPlantId );
        req.setPlantPotSize( convetToPotSize(dataBinding.potSize.getCheckedRadioButtonId() ));
        req.setPlantSize( convetToPlantSize( dataBinding.plantSize.getCheckedRadioButtonId() ));
        long ph, temp, hum, light;

        ph =  Math.round(Float.parseFloat( dataBinding.sensPh.getText().toString() ) * 100.0);
        temp =  Math.round(Float.parseFloat( dataBinding.sensTemp.getText().toString() ) * 100.0);
        hum =  Math.round(Float.parseFloat( dataBinding.sensHumidity.getText().toString() ) * 100.0);
        light =  Math.round(Float.parseFloat( dataBinding.sensLight.getText().toString() ) * 100.0);
        // IF real value is needed divide by / 100

        req.setSensPh(ph);
        req.setSensHumidity(hum);
        req.setSensLight(light);
        req.setSensTemp(temp);

        viewModel.updatePlantSensValue(req).observe(this, responseResource -> {
            switch (responseResource.status){
                case SUCCESS:
                    dataBinding.setLoading(false);
                    Toast.makeText(PlantAnalysisActivity.this, R.string.plant_value_update, Toast.LENGTH_SHORT).show();
                    viewModel.removeRelatedNotifications( userPlantId );
                    redirectToMain();
                    break;
                case ERROR:
                    dataBinding.setLoading(false);
                    Toast.makeText(PlantAnalysisActivity.this, R.string.common_err, Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    dataBinding.setLoading(true);
                    break;
            }
        });
    }

    public boolean validateForm(){
        int potSizeId = dataBinding.potSize.getCheckedRadioButtonId();
        int plantSizeId = dataBinding.plantSize.getCheckedRadioButtonId();
        boolean bad_numbers = false;
        try{
            Float.parseFloat( dataBinding.sensPh.getText().toString() );
            Float.parseFloat( dataBinding.sensTemp.getText().toString() );
            Float.parseFloat( dataBinding.sensHumidity.getText().toString() );
            Float.parseFloat( dataBinding.sensLight.getText().toString() );
        }catch (NumberFormatException e){
            bad_numbers = true;
        }
        return !bad_numbers && !(plantSizeId == -1 || potSizeId == -1);
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

    private void redirectToMain(){
        startActivity(new Intent(PlantAnalysisActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        redirectToMain();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + REQUEST_CODE + " , " + resultCode);
        if( requestCode == REQUEST_CODE) {


            ExifInterface ei = null;
            try {
                ei = new ExifInterface(capturedImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationDegrees = 0;
            Log.d(TAG, "onActivityResult: Orientation " + orientation);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotationDegrees = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotationDegrees = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotationDegrees = 270;
                    break;
            }

            Bitmap src=BitmapFactory.decodeFile(capturedImagePath);

            if(src == null)
            {
                capturedImagePath = "";
                return;
            }

            Bitmap result = rotateImage(src,rotationDegrees);
            try {
                FileOutputStream out = new FileOutputStream(capturedImagePath);
                result.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            dataBinding.capturedPlantImage.setImageBitmap(result);

        }else{
            dataBinding.capturedPlantImage.setImageBitmap(null);
            capturedImagePath = "";
        }
    }



    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity( getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
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
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
    public void onClick(View v) {
        Log.d(TAG, "onClick: " + v.getId());
        switch (v.getId()){
            case R.id.updateSensValueButton:
                if(validateForm()){
                    saveForm();
                }else {
                    Toast.makeText(this,R.string.update_form_alert, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.takePictureButton:
                dispatchTakePictureIntent();
                Log.d(TAG, "onClick: takePicutre");
                break;
        }
    }
}
