package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.entities.Category;
import org.springframework.stereotype.Component;
@Component("categorymapper")
public class CategoryMapper {

    public de.thowl.prog3.exam.web.dto.Category map(Category in) {
        return new de.thowl.prog3.exam.web.dto.Category(in.getId(), in.getName());
    }
}
