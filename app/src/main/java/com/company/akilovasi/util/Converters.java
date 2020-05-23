package com.company.akilovasi.util;

import android.util.Log;

import androidx.databinding.BindingConversion;
import androidx.room.TypeConverter;

import com.company.akilovasi.R;
import com.company.akilovasi.data.local.entities.Notification;
import java.util.Date;
import java.util.IllformedLocaleException;

public class Converters {
    private static final String TAG = "Converters";
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    @TypeConverter
    public static String notificationTypeToString(Notification.Type type){
        return type.name();
    }

    @TypeConverter
    public static Notification.Type stringToNotificationType(String string){
        Notification.Type result = Notification.Type.Default;
        try {
            result = Notification.Type.valueOf(string);
        }catch (IllformedLocaleException e){
            Log.e(TAG, e.toString());
        }
        return result;
    }

    @TypeConverter
    public static String SensorTypeEnumToString(SensorTypes sensorTypes){
        return sensorTypes.getSensorType();
    }

    @TypeConverter
    public static SensorTypes SensorTypeEnumToString(String string){
        SensorTypes result;
        result = SensorTypes.valueOf(string);
        return result;
    }
}

