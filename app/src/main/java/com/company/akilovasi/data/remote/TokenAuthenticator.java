package com.company.akilovasi.data.remote;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.company.akilovasi.data.remote.repositories.UserRepository;
//import com.company.akilovasi.di.SecretPrefs;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import static com.company.akilovasi.data.remote.ApiConstants.ACCESS_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.AUTHORIZATION;
import static com.company.akilovasi.data.remote.ApiConstants.BEARER;
import static com.company.akilovasi.data.remote.ApiConstants.EMPTY_STRING;
import static com.company.akilovasi.data.remote.ApiConstants.REFRESH_TOKEN;
import static com.company.akilovasi.data.remote.ApiConstants.SPACE;

public class TokenAuthenticator implements Authenticator {

    SharedPreferences secretPreferences;

    Lazy<UserRepository> userRepositoryLazyWrapper;

    @Inject
    public TokenAuthenticator(SharedPreferences secretPreferences, Lazy<UserRepository> userRepositoryLazyWrapper) {
        this.secretPreferences = secretPreferences;
        this.userRepositoryLazyWrapper = userRepositoryLazyWrapper;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) {
        UserRepository userRepository = userRepositoryLazyWrapper.get();
        if(userRepository == null){
            return null;
        }

        final String accessToken = secretPreferences.contains(ACCESS_TOKEN) ? secretPreferences.getString(ACCESS_TOKEN, null) : null;
        if (!isRequestWithAccessToken(response) || accessToken == null) {
            //There is no any logged in user
            return null;
        }

        synchronized (this) {
            final String newAccessToken = secretPreferences.contains(ACCESS_TOKEN) ? secretPreferences.getString(ACCESS_TOKEN, EMPTY_STRING) : EMPTY_STRING;
            // Access token is refreshed in another thread.
            if (!accessToken.equals(newAccessToken)) {
                return newRequestWithAccessToken(response.request(), newAccessToken);
            }

            if (responseCount(response) >= 3) {
                return null; // If we've failed 3 times, give up.
            }

            // Need to refresh an access token
            String updatedAccessToken = userRepository.refreshToken(secretPreferences.getString(REFRESH_TOKEN, EMPTY_STRING));
            if(updatedAccessToken != null) {
                secretPreferences.edit().putString(ACCESS_TOKEN, updatedAccessToken).apply();
                return newRequestWithAccessToken(response.request(), updatedAccessToken);
            }
            else{
                return null;
            }
        }
    }

    private boolean isRequestWithAccessToken(@NonNull Response response) {
        String header = response.request().header(AUTHORIZATION);
        return header != null && header.startsWith(BEARER);
    }

    @NonNull
    private Request newRequestWithAccessToken(@NonNull Request request, @NonNull String accessToken) {
        return request.newBuilder()
                .header(AUTHORIZATION, accessToken)
                .build();
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}

