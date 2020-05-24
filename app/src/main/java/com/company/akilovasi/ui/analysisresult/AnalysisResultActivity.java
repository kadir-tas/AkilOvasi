package com.company.akilovasi.ui.analysisresult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;

import android.os.Bundle;
import android.util.Log;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.databinding.ActivityAnalysisResultBinding;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.analysisresult.callbacks.PdfClick;

public class AnalysisResultActivity extends BaseActivity<AnalysisResultViewModel, ActivityAnalysisResultBinding> implements PdfClick {
    private static final String TAG = "AnalysisResultActivity";
    public static final String PARAM_USER_PLANT_HISTORY = "user_plant_history";

    private PlantHistory mPlantHistory = null;

    @Override
    public Class<AnalysisResultViewModel> getViewModel() {
        return AnalysisResultViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_analysis_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getExtras() != null){
            Object obj = getIntent().getExtras().get(PARAM_USER_PLANT_HISTORY);
            if(obj instanceof PlantHistory){
                mPlantHistory = (PlantHistory) obj;
            }else{
                Log.e(TAG, "onCreate: PARAM " + PARAM_USER_PLANT_HISTORY + " is not instanceof PlantHistory");
            }
        }
        dataBinding.setPlantHistory(mPlantHistory);
        dataBinding.setDownloadPdfClick(this);
        Log.d(TAG, "onCreate: Finished");
    }

    @Override
    protected DataBindingComponent getDataBindingComponent() {
        return null;
    }

    @Override
    public void onPDFButtonClicked(PlantHistory plantHistory) {

    }
}
