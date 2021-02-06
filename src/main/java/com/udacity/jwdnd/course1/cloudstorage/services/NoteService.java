package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }


    public List<Note> getAllNotesById(int userId) {
        return noteMapper.getNotesById(userId);
    }

    public int addNote(Note note) {
        return noteMapper.addNote(note);
    }

    public Note getNoteById(long noteId) {
        return noteMapper.getNoteById(noteId);
    }

    public int deleteNoteById(Integer noteId) {
        return noteMapper.deleteNoteById(noteId);
    }
}
