package com.company.akilovasi.data.remote.repositoriesImpl;

import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.BannerDao;
import com.company.akilovasi.data.local.dao.PlantDao;
import com.company.akilovasi.data.local.dao.PlantHistoryDao;
import com.company.akilovasi.data.local.dao.UserDao;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.local.entities.User;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.UserService;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.LoginRequest;
import com.company.akilovasi.data.remote.models.requests.LogoutRequest;
import com.company.akilovasi.data.remote.models.requests.RegisterUserRequest;
import com.company.akilovasi.data.remote.models.requests.ResetPasswordRequest;
import com.company.akilovasi.data.remote.models.requests.UpdateUserRequest;
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
    private PlantDao plantDao;
    private BannerDao bannerDao;
    private PlantHistoryDao plantHistoryDao;

    @Inject
    public UserRepositoryImpl(Retrofit retrofit, Gson gson, UserDao userDao, PlantDao plantDao, BannerDao bannerDao, PlantHistoryDao plantHistoryDao) {
        this.gson = gson;
        this.retrofit = retrofit;
        this.userDao = userDao;
        this.plantDao = plantDao;
        this.plantHistoryDao = plantHistoryDao;
        this.bannerDao = bannerDao;
        userService = this.retrofit.create(UserService.class);
    }

    @Override
    public LiveData<Resource<LoginResponse>> doAuth(LoginRequest loginRequest) {
        final MediatorLiveData<Resource<LoginResponse>> result = new MediatorLiveData<>();
        result.setValue(Resource.loading(null));
        userService.login(loginRequest).enqueue(new Callback<com.company.akilovasi.data.remote.models.responses.Response<LoginResponse>>() {
            @Override
            public void onResponse(Call<com.company.akilovasi.data.remote.models.responses.Response<LoginResponse>> call, Response<com.company.akilovasi.data.remote.models.responses.Response<LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(Resource.success(response.body().getResults()));
                } else {
                    try {
                        result.setValue(Resource.error(response.errorBody().string(), null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<com.company.akilovasi.data.remote.models.responses.Response<LoginResponse>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage(), null));
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<com.company.akilovasi.data.remote.models.responses.Response<Message>>> logout(LogoutRequest logoutRequest) {
        final MediatorLiveData<Resource<com.company.akilovasi.data.remote.models.responses.Response<Message>>> result = new MediatorLiveData<>();
        result.setValue(Resource.loading(null));
        userService.logout(logoutRequest).enqueue(new Callback<com.company.akilovasi.data.remote.models.responses.Response<Message>>() {
            @Override
            public void onResponse(Call<com.company.akilovasi.data.remote.models.responses.Response<Message>> call, Response<com.company.akilovasi.data.remote.models.responses.Response<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new ClearUserInfoAsyncTask(userDao).execute();
                    new ClearPlantsAsyncTask(plantDao).execute();
                    new ClearPlantHistoryAsyncTask(plantHistoryDao).execute();
                    new ClearBannersAsyncTask(bannerDao).execute();
                    result.setValue(Resource.success(response.body()));
                } else {
                    result.setValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<com.company.akilovasi.data.remote.models.responses.Response<Message>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage(), null));
            }
        });
        return result;
    }

    @Override
    public String refreshToken(String refreshToken) {
        try {
            Response response = userService.refreshJwtToken(refreshToken).execute();
            if (response.isSuccessful()) {
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

    @Override
    public Call<com.company.akilovasi.data.remote.models.responses.Response<Message>> registerUser(RegisterUserRequest registerUserRequest) {
        return userService.register(registerUserRequest);
    }

    @Override
    public LiveData<Resource<User>> getUserData() {
        return new NetworkBoundResource<User, com.company.akilovasi.data.remote.models.responses.Response<User>>() {

            @Override
            protected void saveCallResult(@NonNull com.company.akilovasi.data.remote.models.responses.Response<User> item) {
                if (item.getResults() != null) {
                    userDao.clearUsers();
                }
                userDao.saveUser(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.loadUser();
            }

            @NonNull
            @Override
            protected Call<com.company.akilovasi.data.remote.models.responses.Response<User>> createCall() {
                return userService.getUserData();
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<Resource<User>> updateUser(UpdateUserRequest updateUserRequest) {
        return new NetworkBoundResource<User, com.company.akilovasi.data.remote.models.responses.Response<User>>() {

            @Override
            protected void saveCallResult(@NonNull com.company.akilovasi.data.remote.models.responses.Response<User> item) {
                if (item.getResults() != null) userDao.saveUser(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.loadUser();
            }

            @NonNull
            @Override
            protected Call<com.company.akilovasi.data.remote.models.responses.Response<User>> createCall() {
                return userService.updateUser(updateUserRequest);
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<Resource<com.company.akilovasi.data.remote.models.responses.Response<Message>>> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        final MediatorLiveData<Resource<com.company.akilovasi.data.remote.models.responses.Response<Message>>> result = new MediatorLiveData<>();
        result.setValue(Resource.loading(null));
        userService.resetPassword(resetPasswordRequest).enqueue(new Callback<com.company.akilovasi.data.remote.models.responses.Response<Message>>() {
            @Override
            public void onResponse(Call<com.company.akilovasi.data.remote.models.responses.Response<Message>> call, Response<com.company.akilovasi.data.remote.models.responses.Response<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(Resource.success(response.body()));
                } else {
                    result.setValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<com.company.akilovasi.data.remote.models.responses.Response<Message>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage(), null));
            }
        });
        return result;
    }

    private static class ClearPlantsAsyncTask extends AsyncTask<Void, Void, Void> {
        private PlantDao plantDao;

        private ClearPlantsAsyncTask(PlantDao plantDao) {
            this.plantDao = plantDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            plantDao.clearPlants();
            return null;
        }
    }

    private static class ClearPlantHistoryAsyncTask extends AsyncTask<Void, Void, Void> {
        private PlantHistoryDao plantHistoryDao;

        private ClearPlantHistoryAsyncTask(PlantHistoryDao plantHistoryDao) {
            this.plantHistoryDao = plantHistoryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            plantHistoryDao.clearPlantsHistory();
            return null;
        }
    }

    private static class ClearBannersAsyncTask extends AsyncTask<Void, Void, Void> {
        private BannerDao bannerDao;

        private ClearBannersAsyncTask(BannerDao bannerDao) {
            this.bannerDao = bannerDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bannerDao.clearBanners();
            return null;
        }
    }

    private static class ClearUserInfoAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;

        private ClearUserInfoAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.clearUsers();
            return null;
        }
    }

}

