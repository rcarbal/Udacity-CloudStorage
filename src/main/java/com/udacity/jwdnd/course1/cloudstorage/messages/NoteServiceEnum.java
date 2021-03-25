package com.udacity.jwdnd.course1.cloudstorage.messages;

public enum NoteServiceEnum {

    NOTE_SAVED("Note successfully saved."),
    DENIED("You do note have permission to edit this note"),
    DELETED("Note successfully delete"),
    ERROR("Something went wrong"),
    NOTE_UPDATED("Note was successfully updated.");

    private String note;

    NoteServiceEnum(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
