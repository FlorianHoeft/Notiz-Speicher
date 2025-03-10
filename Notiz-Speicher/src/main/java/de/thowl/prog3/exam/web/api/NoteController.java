package de.thowl.prog3.exam.web.api;

import java.util.ArrayList;
import java.util.List;

import de.thowl.prog3.exam.storage.entities.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.web.mapper.NoteMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    @Autowired
    @Qualifier("notemapper")
    private NoteMapper mapper = new NoteMapper();

    @Autowired
    private NoteService service;

    public NoteController() {
        log.debug("entering ctor");
    }

    @GetMapping("/")
    public List<de.thowl.prog3.exam.web.dto.Note> getNotes() {
        log.debug("entering getNotes");
        List<de.thowl.prog3.exam.web.dto.Note> result = new ArrayList<>();
        List<Note> notes = this.service.getAllNotes();
        for (Note n : notes) {
            result.add(this.mapper.map(n));
        }
        return result;
    }

    @GetMapping("/{id}")
    public de.thowl.prog3.exam.web.dto.Note getNotebyId(@PathVariable Long id) {
        log.debug("entering getNoteById, id={}", id);
        Note n = this.service.getNote(id);
        return this.mapper.map(n);
    }

    @GetMapping("/user/{userId}")
    public List<Note> getNoteByUserId(@PathVariable Long userId) {
        return service.getNoteByUserId(userId);
    }
}
