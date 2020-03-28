package com.company.akilovasi.ui.login.fragments;

import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.RegisterUserRequest;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.UserRepository;

import javax.inject.Inject;

import retrofit2.Call;

public class RegisterViewModel extends ViewModel {

    UserRepository userRepository;

    @Inject
    public RegisterViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Call<Response<Message>> registerUser(RegisterUserRequest registerUserRequest){
        return userRepository.registerUser(registerUserRequest);
    }

}
