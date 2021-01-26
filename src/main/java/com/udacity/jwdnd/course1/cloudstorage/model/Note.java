package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {

    private Integer noteId;
    private String noteTitle;
    private String noteDescriptrion;
    private Integer userId;

    public Note(Integer noteId, String noteTitle, String noteDescriptrion, Integer userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescriptrion = noteDescriptrion;
        this.userId = userId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescriptrion() {
        return noteDescriptrion;
    }

    public void setNoteDescriptrion(String noteDescriptrion) {
        this.noteDescriptrion = noteDescriptrion;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteDescriptrion='" + noteDescriptrion + '\'' +
                ", userId=" + userId +
                '}';
    }
}
