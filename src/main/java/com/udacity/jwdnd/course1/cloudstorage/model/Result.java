package com.udacity.jwdnd.course1.cloudstorage.model;

public class Result {

    private String key;
    private String message;

    public Result(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "key='" + key + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
