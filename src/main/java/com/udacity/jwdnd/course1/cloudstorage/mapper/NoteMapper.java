package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotesById(int userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNoteById(long noteId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int deleteNoteById(Integer noteId);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid =#{noteId}")
    int updateNote(Note note);
}
