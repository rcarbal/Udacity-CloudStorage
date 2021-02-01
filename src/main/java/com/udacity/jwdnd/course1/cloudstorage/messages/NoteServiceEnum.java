package com.udacity.jwdnd.course1.cloudstorage.messages;

public enum NoteServiceEnum {

    NOTE_SAVED("Note successfully saved.");

    private String note;

    NoteServiceEnum(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
