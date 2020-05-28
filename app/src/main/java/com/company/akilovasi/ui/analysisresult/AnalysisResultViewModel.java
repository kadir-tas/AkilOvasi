package com.company.akilovasi.ui.analysisresult;

import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.repositories.NotificationRepository;
import com.company.akilovasi.data.remote.repositories.PlantHistoryRepository;
import com.company.akilovasi.di.SecretPrefs;
import java.util.List;
import javax.inject.Inject;

public class AnalysisResultViewModel extends ViewModel {
    private static final String TAG = "AnalysisResultViewModel";

    private NotificationRepository notificationRepository;

    private PlantHistoryRepository plantHistoryRepository;

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    public AnalysisResultViewModel(NotificationRepository notificationRepository, PlantHistoryRepository plantHistoryRepository) {
        this.notificationRepository = notificationRepository;
        this.plantHistoryRepository = plantHistoryRepository;
    }

    public void pollNotifications(){
        notificationRepository.pollNotifications(secretPreferences.getLong(ApiConstants.USER_ID, -1));
    }

    public void removeByUserPlantIdAndType(Notification.Type type , Long userPlantId){
        notificationRepository.deleteNotificationByTypeAndPlantId( type , userPlantId );
    }

    public LiveData<Resource<List<PlantHistory>>> getPlantHistoryPaged(Long userPlantId, int page){
        return plantHistoryRepository.getUserPlantHistoryPaged( userPlantId,page);
    }
}
