package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notes")

public class Note {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    @Size(min = 5, max = 30)
    private String title;

    @Column(name = "content")
    @Size(min = 5, max = 300)
    private String content;

    @Column(name = "category")
    @Size(min = 5, max = 30)
    private String category;
}
