package com.company.akilovasi.data.remote.repositoriesImpl;

import com.company.akilovasi.data.remote.repositories.LoginRepository;
import com.google.gson.Gson;


import retrofit2.Retrofit;

public class LoginRepositoryImpl implements LoginRepository {
    public LoginRepositoryImpl(Retrofit retrofit, Gson gson) {
    }
}


































































//    private final UserDao userDao;
//    private final UserService userService;
//
//    @Inject
//    public LoginRepositoryImpl(UserDao userDao, UserService userService) {
//        this.userDao = userDao;
//        this.userService = userService;
//    }
//
//    public LoginRepositoryImpl(Retrofit retrofit, Gson gson) {
//    }
//
//    public LiveData<Resource<List<User>>> loadPopularMovies() {
//        return new NetworkBoundResource<List<User>, LoginResponse>() {
//
//            @Override
//            protected void saveCallResult(@NonNull LoginResponse resp) {
//                userDao.saveLogin(resp.isLogin());
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<Boolean> loadFromDb() {
//                return userDao.loadLogin();
//            }
//
//            @NonNull
//            @Override
//            protected Call<LoginResponse> createCall() {
//                return userService.login();
//            }
//        }.getAsLiveData();
//    }
//
//    public List<User> getLogin(int id){
//        return userDao.loadLogin();
//    }
//
//}


