package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * This Class creates a Table called Note in the Database
 * This Class uses JPA annotations for Object mapping
 */
@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notes")
public class Note{
    /**
     * Id is generated when new Note is created
     * @param GeneratedValue generates a value when new entry is created
     */
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    @Size(min = 1, max = 80)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "favorite")
    private boolean favorite;

    @Column
    private LocalDateTime lastModified;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        lastModified = LocalDateTime.now();
    }


    /**
     * The Column user_Id is here linked to the User Table with a many to one relation
     */
    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;
    /**
     * The Column category_id is here linked to the Category Table with a many to one relation
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;




}
