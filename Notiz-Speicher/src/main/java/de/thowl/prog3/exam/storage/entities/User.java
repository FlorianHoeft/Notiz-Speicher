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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    @Size(min = 3, max = 30)
    private String name;

    @Column(name = "password")
    @Size(min = 5, max = 255)
    private String password;

    @Column(name = "email")
    @Size(min = 5, max = 30)
    private String email;

}
