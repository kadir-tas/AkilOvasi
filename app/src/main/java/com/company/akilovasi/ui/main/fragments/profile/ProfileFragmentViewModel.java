package com.company.akilovasi.ui.main.fragments.profile;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.User;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.ResetPasswordRequest;
import com.company.akilovasi.data.remote.models.requests.UpdateUserRequest;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.UserRepository;
import com.company.akilovasi.di.SecretPrefs;

import javax.inject.Inject;

public class ProfileFragmentViewModel extends ViewModel {

    UserRepository userRepository;


    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    public ProfileFragmentViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LiveData<Resource<User>> updateUserProfile(User user){
        return userRepository.updateUser(new UpdateUserRequest(user));
    }

    public LiveData<Resource<Response<Message>>> resetPassword(ResetPasswordRequest resetPasswordRequest){
        return userRepository.resetPassword(resetPasswordRequest);
    }

    LiveData<Resource<User>> getUserProfile() {
        return userRepository.getUserData();
    }

}
