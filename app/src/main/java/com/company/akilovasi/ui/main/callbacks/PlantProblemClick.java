package com.company.akilovasi.ui.main.callbacks;

import com.company.akilovasi.data.local.entities.AnalysisResult;

public interface PlantProblemClick {
    void onCancelClicked(AnalysisResult analysisResult);

    void onInterfereClicked(AnalysisResult analysisResult);

}
