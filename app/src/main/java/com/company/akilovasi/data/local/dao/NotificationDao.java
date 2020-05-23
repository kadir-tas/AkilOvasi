package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.company.akilovasi.data.local.entities.AnalysisResult;
import com.company.akilovasi.data.local.entities.Notification;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNotifications(List<Notification> notification);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNotification(Notification notification);

    @Delete
    void deleteNotification(Notification notification);

    @Query("DELETE FROM notifications")
    void deleteAllNotifications();

    @Query("DELETE FROM notifications WHERE id = :id")
    void deleteNotificationById(Long id);

    @Query("SELECT * FROM notifications ORDER BY date DESC")
    LiveData<List<Notification>> getAllNotifications();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAnalysisResult(AnalysisResult analysisResult);

    @Query("SELECT * FROM analysis_result WHERE  userPlantId = :userPlantId")
    LiveData<List<AnalysisResult>> getAllAnalysisResults(Long userPlantId);

    @Delete
    void deleteAnalysisResult(AnalysisResult analysisResult);
}
