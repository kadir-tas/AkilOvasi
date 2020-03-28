package com.company.akilovasi.data.remote;

public class ApiConstants {
    public static final int TIMEOUT_IN_SEC = 15;
    public static final String BANNER_IMAGE_ENDPOINT_PREFIX = "banner/img?imageId=";
    public static final String PLANT_TYPE_IMAGE_ENDPOINT_PREFIX = "plant/img?plantId=";
    public static final String USER_PLANTS_IMAGE_ENDPOINT_PREFIX = "garden/img?userPlantId=";
    public static final String HISTORIC_PLANTS_IMAGE_ENDPOINT_PREFIX = "historical/userPlant/img?historicalId=";
    public static final String REFRESH_JWT_TOKEN_URL = "api/auth/token";
    public static final String LOGOUT_URL = "api/auth/logout";
    public static final String REGISTER_URL = "user/register";
    public static final String LOGIN_URL = "api/auth/login";
    public static final String SIGNUP_URL = "/sign-up";
    public static final String METHOD_TYPE_POST = "POST";
    public static final String METHOD_TYPE_GET = "GET";
    public static final String METHOD_TYPE_PUT = "PUT";
    public static final String METHOD_TYPE_UPDATE = "UPDATE";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String USER_ID = "user_id";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
}
