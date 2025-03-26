package de.thowl.prog3.exam.storage.repositories;

import de.thowl.prog3.exam.storage.entities.Attachements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachementsRepository extends JpaRepository<Attachements, Long> {
}
