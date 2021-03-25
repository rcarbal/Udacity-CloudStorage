package com.udacity.jwdnd.course1.cloudstorage.messages;

public enum FileServiceEnum {

    FILE_SAVED("File successfully saved"),
    NO_FILE_FOUND("No file selected."),
    FILE_DELETED("File successfully deleted."),
    FILE_DELETE_ERROR("There was an error deleting file");

    private String message;

    FileServiceEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
