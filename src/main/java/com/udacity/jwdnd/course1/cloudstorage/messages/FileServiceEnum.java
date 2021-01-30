package com.udacity.jwdnd.course1.cloudstorage.messages;

public enum FileServiceEnum {

    FILE_SAVED("File successfully saved");

    private String message;

    FileServiceEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
