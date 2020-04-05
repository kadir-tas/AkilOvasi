package com.company.akilovasi.data.remote.models.requests;

import com.company.akilovasi.data.local.entities.User;

import java.util.Date;

public class UpdateUserRequest {

    private Long userId;
    private String username;
    private String userFirstname;
    private String userLastname;
    private Date userRegistrationDate;
    private String userHomeAddress;
    private String userEmail;
    private String userPhone;


    public UpdateUserRequest(Long userId, String username, String userFirstname, String userLastname, Date userRegistrationDate, String userHomeAddress, String userEmail, String userPhone) {
        this.userId = userId;
        this.username = username;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.userRegistrationDate = userRegistrationDate;
        this.userHomeAddress = userHomeAddress;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public UpdateUserRequest(User user){
        this.username = user.getUsername();
        this.userFirstname = user.getUserFirstname();
        this.userLastname = user.getUserLastname();
        this.userHomeAddress = user.getUserHomeAddress();
        this.userPhone = user.getUserPhone();
        this.userEmail = user.getUserEmail();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getUserRegistrationDate() {
        return userRegistrationDate;
    }

    public void setUserRegistrationDate(Date userRegistrationDate) {
        this.userRegistrationDate = userRegistrationDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getUserHomeAddress() {
        return userHomeAddress;
    }

    public void setUserHomeAddress(String userHomeAddress) {
        this.userHomeAddress = userHomeAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

}
