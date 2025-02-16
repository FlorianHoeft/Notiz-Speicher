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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username")
    @Size(min = 5, max = 30)
    private String name;

    @Column(name = "password")
    @Size(min = 5, max = 30)
    private String password;

    @Column(name = "email")
    @Size(min = 5, max = 30)
    private String email;

}
