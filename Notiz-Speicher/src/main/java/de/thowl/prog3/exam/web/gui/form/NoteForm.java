package de.thowl.prog3.exam.web.gui.form;

import lombok.Data;
/**
 * This class is used to collect Note-related data
 * The @Data annotation from Lombok automatically generates getters, setters, equals, hashCode, and toString methods
 */
@Data
public class NoteForm {
    public String title;
}
