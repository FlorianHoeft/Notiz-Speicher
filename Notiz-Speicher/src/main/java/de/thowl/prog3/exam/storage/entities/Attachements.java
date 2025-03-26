package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This Class creates a Table called Note in the Database
 * This Class uses JPA annotations for Object mapping
 */
@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "attachaments")
public class Attachements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName; // Originaler Dateiname

    @Column(nullable = false)
    private String filePath; // Pfad zur Datei auf dem Server

    @Column(nullable = false)
    private String type; // "FILE" für Bilder/Dateien, "LINK" für URLs

    @ManyToOne
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;




}
