package com.company.akilovasi.data.remote.repositories;

import com.company.akilovasi.data.remote.models.responses.LoginResponse;

public interface UserRepository {

    public LoginResponse doAuth(String username, String password);


}