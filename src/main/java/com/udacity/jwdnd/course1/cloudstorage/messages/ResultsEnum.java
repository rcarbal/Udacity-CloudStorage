package com.udacity.jwdnd.course1.cloudstorage.messages;

public enum ResultsEnum {
    SUCCESS("success"),
    FAILED("fail"),
    ERROR("error");

    private String key;

    ResultsEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
