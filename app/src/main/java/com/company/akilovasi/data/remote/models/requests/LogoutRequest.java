package com.company.akilovasi.data.remote.models.requests;

public class LogoutRequest {

    private Long userId;

    public LogoutRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
