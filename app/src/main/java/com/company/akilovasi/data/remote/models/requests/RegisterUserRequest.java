package com.company.akilovasi.data.remote.models.requests;

public class RegisterUserRequest {

    private String username;
    private String userFirstname;
    private String userLastname;
    private String userPassword;
    private String userHomeAddress;
    private String userEmail;
    private String userPhone;

    public RegisterUserRequest() {
    }

    public RegisterUserRequest(String username, String userFirstname, String userLastname, String userPassword, String userHomeAddress, String userEmail, String userPhone) {
        this.username = username;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.userPassword = userPassword;
        this.userHomeAddress = userHomeAddress;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserHomeAddress() {
        return userHomeAddress;
    }

    public void setUserHomeAddress(String userHomeAddress) {
        this.userHomeAddress = userHomeAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
