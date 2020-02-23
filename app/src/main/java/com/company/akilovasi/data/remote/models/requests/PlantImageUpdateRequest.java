package com.company.akilovasi.data.remote.models.requests;

import okhttp3.MultipartBody;

public class PlantImageUpdateRequest {
    private Long userId;
    private Long userPlantId;

    private MultipartBody multipartBody;

    public PlantImageUpdateRequest(Long userId, Long userPlantId, MultipartBody multipartBody) {
        this.userId = userId;
        this.userPlantId = userPlantId;
        this.multipartBody = multipartBody;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserPlantId() {
        return userPlantId;
    }

    public void setUserPlantId(Long userPlantId) {
        this.userPlantId = userPlantId;
    }

    public MultipartBody getMultipartBody() {
        return multipartBody;
    }

    public void setMultipartBody(MultipartBody multipartBody) {
        this.multipartBody = multipartBody;
    }
}
