package de.thowl.prog3.exam.service;

import java.util.List;

import de.thowl.prog3.exam.storage.entities.Note;

public interface NoteService {

    public Note getNote(long id);

    public Note getNote(String title);

    public List<Note> getAllNotes();

    List<Note> getNoteByUserId(Long userId);
}
