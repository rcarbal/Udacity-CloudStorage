package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.messages.NoteServiceEnum;
import com.udacity.jwdnd.course1.cloudstorage.messages.ResultsEnum;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.Result;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    private String addNote(@ModelAttribute("note")Note note, Authentication auth, Model model){

        int userId = userService.getUserById(auth.getName());
        note.setUserId(userId);

        int numSavedNotes = noteService.addNote(note);

        if (numSavedNotes > 0){
            model.addAttribute("result",
                    new Result(ResultsEnum.SUCCESS.getKey(), NoteServiceEnum.NOTE_SAVED.getNote()));
        }

        return "result";
    }

    @GetMapping
    private String deleteNote(@PathVariable long noteId, Authentication auth, Model model){
        int userId = userService.getUserById(auth.getName());

        Note note = noteService.getNoteById(noteId);
        int userIdOfNote = note.getUserId();

        if (userId != userIdOfNote){
            model.addAttribute("result", new Result(ResultsEnum.FAILED.getKey(), NoteServiceEnum.DENIED.getNote()));
        } else {
            int numNotesDeleted = noteService.deleteNoteById(note.getNoteId());

            if (numNotesDeleted > 0){
                model.addAttribute("result", new Result(ResultsEnum.SUCCESS.getKey(), NoteServiceEnum.DELETED.getNote()));
            } else {
                model.addAttribute("result", new Result(ResultsEnum.ERROR.getKey(), NoteServiceEnum.ERROR.getNote()));
            }
        }

        return "result";
    }
}
