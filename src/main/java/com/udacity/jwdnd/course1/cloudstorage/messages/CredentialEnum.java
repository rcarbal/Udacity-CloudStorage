package com.udacity.jwdnd.course1.cloudstorage.messages;

public enum CredentialEnum {
    SAVED("Successfully Saved Credentials"),
    UPDATE("Successfully Updated Credentials"),
    DELETED("Successfully Deleted Credentials");

    private String message;

    CredentialEnum(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
