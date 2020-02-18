package com.company.akilovasi.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.remote.repositories.UserRepository;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;

    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Boolean> login(String username, String password){
        userRepository.doAuth(username,password);
        return null;
    }

}