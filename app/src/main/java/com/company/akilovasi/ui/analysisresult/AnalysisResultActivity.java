package com.company.akilovasi.ui.analysisresult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.AnalysisResult;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.databinding.ActivityAnalysisResultBinding;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.analysisresult.callbacks.PdfClick;
import com.company.akilovasi.ui.main.MainActivity;

import java.util.List;

public class AnalysisResultActivity extends BaseActivity<AnalysisResultViewModel, ActivityAnalysisResultBinding> implements PdfClick {
    private static final String TAG = "AnalysisResultActivity";
    public static final String PARAM_USER_PLANT_HISTORY = "user_plant_history";
    public static final String PARAM_USER_PLANT_ID = "userPlantId"; /*For FCM since it will send data from userPlantId*/

    private PlantHistory mPlantHistory = null;

    @Override
    public Class<AnalysisResultViewModel> getViewModel() {
        return AnalysisResultViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_analysis_result;
    }

    // This class currently have multiple methods for receiving its parameter, either via PARAM_USER_PLANT_HISTORY which can be object or userPlantId, or PARAM_USER_PLANT_ID which
    // is only userPlantId, this is not necessary, however PARAM_USER_PLANT_ID needs to exists since upon receiving FCM message in the background, when user clicks the notification
    // android will direct the user in this activity (check AndroidManifest) FCM messages sent from backend has 'userPlantId' parameter in their payload if required.
    // So upon receiving this message this activity can draw the latest historical or any other appropriate data from this source and show it.
    //TODO: Retrieve correct data from single id rather the passing objects maybe ?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding.setLoading(true);
        if(getIntent().getExtras() != null){
            Object obj = getIntent().getExtras().get(PARAM_USER_PLANT_HISTORY);

            /*When notification is received on the background, 'userPlantId' parameter contains the correct plant id, which we want to show latest result for */
            /* This does not apply if PARAM_USER_PLANT_HISTORY set since this param is always supplied from inside the application before starting this activity hence obj is not null*/
            if(obj == null)
                obj = getIntent().getExtras().getString(PARAM_USER_PLANT_ID);

            if(obj instanceof PlantHistory){ /*if PlantHistory is directly supplied as object, set the mPlantHistory to the supplied parameter*/
                mPlantHistory = (PlantHistory) obj;
                setBindings();
            }else if(obj instanceof Long){ /*if PlantHistory is supplied as Long 'id' get the latest one and assign to PlantHistory*/
                Long userPlantId = (Long) obj;
                retrieveLatestFromRepoAndSetBindings(userPlantId);
            }else if(obj instanceof String){ /*if PlantHistory is supplied as String 'id', convert it to long, get the latest one, and assign to PlantHistory*/
                Long userPlantId = Long.parseLong((String) obj);
                retrieveLatestFromRepoAndSetBindings(userPlantId);
            }
            else{
                Log.e(TAG, "onCreate: PARAM " + PARAM_USER_PLANT_HISTORY + " is not instanceof PlantHistory, Long or String");
            }
        }

        Log.d(TAG, "onCreate: Finished");
        viewModel.pollNotifications();
    }

    private void retrieveLatestFromRepoAndSetBindings(Long userPlantId){
        final LiveData<Resource<List<PlantHistory>>> plantHistoryPaged = viewModel.getPlantHistoryPaged(userPlantId, 0); /* PlantHistory always sorted as from latest to oldest  */
        plantHistoryPaged.observe(this, listResource -> {
            if(listResource.data != null && listResource.data.size() > 0){
                mPlantHistory = listResource.data.get(0);
                setBindings();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.removeByUserPlantIdAndType(Notification.Type.AnalysisResult , mPlantHistory.getUserPlantId()); /*Remove the appropriate notifications upon exiting*/
    }

    private void setBindings(){
        dataBinding.setPlantHistory(mPlantHistory);
        dataBinding.setDownloadPdfClick(this);
        dataBinding.setLoading(false);
    }

    @Override
    protected DataBindingComponent getDataBindingComponent() {
        return null;
    }

    @Override
    public void onPDFButtonClicked(PlantHistory plantHistory) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final Intent intent = new Intent(AnalysisResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
