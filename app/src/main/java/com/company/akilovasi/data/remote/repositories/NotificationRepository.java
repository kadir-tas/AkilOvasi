package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.local.entities.AnalysisResult;
import com.company.akilovasi.data.local.entities.Notification;

import java.util.List;

public interface NotificationRepository {
    void deleteNotification(Notification notification);

    void addNotification(Notification notification);

    void deleteAllNotifications();

    LiveData<List<Notification>> getAllNotifications();

    void addNotifications(List<Notification> notifications);

    LiveData<List<AnalysisResult>> getAllAnalysisResults(Long plantId);

    void deleteProblem(AnalysisResult analysisResult);
}
