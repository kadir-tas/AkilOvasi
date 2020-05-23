package com.company.akilovasi.util;

public enum SensorTypes {

    SOIL_TEMP("Toprak Sıcaklık"),

    SOIL_HUM("Toprak Nem"),

    AIR_TEMP("Hava Sıcaklık"),

    AIR_HUM("Hava Nem"),

    AIR_PRESSURE("Basınç"),

    ENVIRONMENT_LIGHT("Ortam Işığı");

    private final String sensorType;

    SensorTypes(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorType() {
        return this.name();
    }

    public String getSensorString(){
        return this.sensorType;
    }
}


