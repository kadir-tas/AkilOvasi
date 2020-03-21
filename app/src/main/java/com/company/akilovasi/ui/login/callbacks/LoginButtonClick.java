package com.company.akilovasi.ui.login.callbacks;

import com.company.akilovasi.data.remote.models.requests.LoginRequest;

public interface LoginButtonClick {

    void onLoginButtonClicked(String usernameOrEmail, String password);

}
