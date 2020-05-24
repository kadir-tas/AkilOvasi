package com.company.akilovasi.util;

public class AppConstants {

    /**
     * Channel id for app notifications
     */
    public static String CHANNEL_ID = "akilovasi-notification";

    public static final String SECRET_PREFERENCE_FILE_NAME = "secrets";

    public static final SensorTypes[] EXISTING_SENSOR_TYPES = {
            SensorTypes.SOIL_TEMP,
            SensorTypes.SOIL_HUM,
            SensorTypes.AIR_TEMP,
            SensorTypes.AIR_HUM,
            SensorTypes.AIR_PRESSURE,
            SensorTypes.ENVIRONMENT_LIGHT
    };
}
