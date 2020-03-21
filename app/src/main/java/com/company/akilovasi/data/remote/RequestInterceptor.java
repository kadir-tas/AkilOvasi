package com.company.akilovasi.data.remote;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.company.akilovasi.data.remote.ApiConstants.ACCESS_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.AUTHORIZATION;
import static com.company.akilovasi.data.remote.ApiConstants.CONTENT_TYPE;
import static com.company.akilovasi.data.remote.ApiConstants.CONTENT_TYPE_APPLICATION_JSON;
import static com.company.akilovasi.data.remote.ApiConstants.EMPTY_STRING;
import static com.company.akilovasi.data.remote.ApiConstants.LOGIN_URL;
import static com.company.akilovasi.data.remote.ApiConstants.METHOD_TYPE_POST;
import static com.company.akilovasi.data.remote.ApiConstants.REFRESH_JWT_TOKEN_URL;
import static com.company.akilovasi.data.remote.ApiConstants.SIGNUP_URL;

public class RequestInterceptor implements Interceptor {

    private SharedPreferences secretPreferences;

    @Inject
    public RequestInterceptor(SharedPreferences secretPreferences) {
        this.secretPreferences = secretPreferences;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

        if ((originalHttpUrl.encodedPath().contains(LOGIN_URL) && originalRequest.method().contains(METHOD_TYPE_POST))
                || (originalHttpUrl.encodedPath().contains(SIGNUP_URL) && originalRequest.method().equals(METHOD_TYPE_POST)
                || originalHttpUrl.encodedPath().contains(REFRESH_JWT_TOKEN_URL))
        ) {
            return  chain.proceed(originalRequest);
        }

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .addHeader(AUTHORIZATION, secretPreferences.contains(ACCESS_TOKEN) ? secretPreferences.getString(ACCESS_TOKEN, EMPTY_STRING) : EMPTY_STRING)
                .addHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON)
                .url(originalHttpUrl);

        return chain.proceed(requestBuilder.build());
    }
}