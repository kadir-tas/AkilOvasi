package com.company.akilovasi.data.remote.repositoriesImpl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.UserDao;
import com.company.akilovasi.data.remote.api.UserService;
import com.company.akilovasi.data.remote.models.requests.LoginRequest;
import com.company.akilovasi.data.remote.models.responses.LoginResponse;
import com.company.akilovasi.data.remote.repositories.UserRepository;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.company.akilovasi.data.remote.ApiConstants.AUTHORIZATION;
import static com.company.akilovasi.data.remote.ApiConstants.BEARER;
import static com.company.akilovasi.data.remote.ApiConstants.SPACE;

public class UserRepositoryImpl implements UserRepository {

    private Retrofit retrofit;
    private Gson gson;
    private UserService userService;
    private UserDao userDao;

    @Inject
    public UserRepositoryImpl(Retrofit retrofit, Gson gson, UserDao userDao) {
        this.gson = gson;
        this.retrofit = retrofit;
        this.userDao = userDao;
        userService = this.retrofit.create(UserService.class);

    }

    @Override
    public LiveData<Resource<LoginResponse>> doAuth(LoginRequest loginRequest) {
        final MediatorLiveData<Resource<LoginResponse>> result = new MediatorLiveData<>();
        result.setValue(Resource.loading(null));
        userService.login(loginRequest).enqueue(new Callback<com.company.akilovasi.data.remote.models.responses.Response<LoginResponse>>() {
            @Override
            public void onResponse(Call<com.company.akilovasi.data.remote.models.responses.Response<LoginResponse>> call, Response<com.company.akilovasi.data.remote.models.responses.Response<LoginResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    result.setValue(Resource.success(response.body().getResults()));
                }else{
                    result.setValue(Resource.error(response.message(),null));
                }
            }
            @Override
            public void onFailure(Call<com.company.akilovasi.data.remote.models.responses.Response<LoginResponse>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage(),null));
            }
        });
        return result;
    }

    @Override
    public String refreshToken(String refreshToken) {
        try {
            Response response = userService.refreshJwtToken(refreshToken).execute();
            if(response.isSuccessful()) {
                String updatedToken = BEARER + SPACE + /*reverse(*/response.headers().get(AUTHORIZATION)/*)*/;
                if (updatedToken.length() > 7) {
                    return updatedToken;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private static String reverse(final String input) {
//        if(input == null || input.isEmpty()){
//            return null;
//        }
//        final StringBuilder builder = new StringBuilder(input);
//        int length = builder.length();
//        for (int i = 0; i < length / 2; i++) {
//            final char current = builder.charAt(i);
//            final int otherEnd = length - i - 1;
//            builder.setCharAt(i, builder.charAt(otherEnd));
//            builder.setCharAt(otherEnd, current);
//        }
//        return builder.toString();
//    }

}
