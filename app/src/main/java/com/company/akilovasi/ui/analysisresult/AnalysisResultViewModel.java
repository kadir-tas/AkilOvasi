package com.company.akilovasi.ui.analysisresult;

import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.remote.repositories.NotificationRepository;


import javax.inject.Inject;

public class AnalysisResultViewModel extends ViewModel {
    private static final String TAG = "AnalysisResultViewModel";

    NotificationRepository notificationRepository;

    @Inject
    public AnalysisResultViewModel(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
}
