package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * This Class creates a Table called Category in the Database
 * This Class uses JPA annotations for Object mapping
 */
@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "category")

public class Category {
    /**
     * Id is generated when new Category is created
     * @param GeneratedValue generates a value when new entry is created
     */
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;
    /**
     * The Column user_id is here linked to the User Table with a many to one relation
     */
    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;


}
