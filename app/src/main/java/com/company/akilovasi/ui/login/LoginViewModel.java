package com.company.akilovasi.ui.login;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.remote.models.requests.LoginRequest;
import com.company.akilovasi.data.remote.models.responses.LoginResponse;
import com.company.akilovasi.data.remote.repositories.UserRepository;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;

    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    private MutableLiveData<LoginRequest> userMutableLiveData;

    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Resource<LoginResponse>> login(LoginRequest loginRequest){
        return userRepository.doAuth(loginRequest);
    }


    public MutableLiveData<LoginRequest> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }



    public void onClick(View view) {

        LoginRequest loginRequest = new LoginRequest(EmailAddress.getValue(), Password.getValue());

        userMutableLiveData.setValue(loginRequest);

    }

}