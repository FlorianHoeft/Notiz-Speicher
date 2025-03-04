package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.gui.form.UserForm;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.web.gui.form.NoteForm;
import de.thowl.prog3.exam.web.mapper.NoteMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NoteFormController {

    @Autowired
    @Qualifier("notemapper")
    private NoteMapper mapper = new NoteMapper();

    @Autowired
    NoteService svc;


}
