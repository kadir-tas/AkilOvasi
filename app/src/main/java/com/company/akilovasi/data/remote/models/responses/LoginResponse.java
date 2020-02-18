package com.company.akilovasi.data.remote.models.responses;

import com.company.akilovasi.data.local.entities.User;

import java.util.List;

public class LoginResponse {

    private String token;

    private List<User> isLogin;

    private String name;

    private String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<User> isLogin() {
        return isLogin;
    }

    public void setLogin(List<User> login) {
        isLogin = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String id;

    private String phone;

}
