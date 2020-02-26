package com.company.akilovasi.data.remote.models.responses;

public class Response <T> {
    private Boolean success;
    private Integer count;
    private T results;

    public Response(Boolean success, Integer count, T results) {
        this.success = success;
        this.count = count;
        this.results = results;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
