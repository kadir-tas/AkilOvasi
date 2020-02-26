package com.company.akilovasi.data.remote.models.other;

public class Message {
    private String statusMessage;

    public Message(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Message() {

    }

    public  String getStatusMessage() {
        return statusMessage;
    }
}
