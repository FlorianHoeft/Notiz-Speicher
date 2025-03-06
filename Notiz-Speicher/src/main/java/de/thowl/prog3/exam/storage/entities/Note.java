package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
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
    @Size(min = 1, max = 999)
    private String content;

    @Column(name = "category")
    @Size(min = 3, max = 30)
    private String category;

    @Column(name = "favorite")
    @Size(min = 1, max = 30)
    private boolean favorite;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userid;



}
