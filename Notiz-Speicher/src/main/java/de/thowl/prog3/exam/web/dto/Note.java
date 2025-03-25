package de.thowl.prog3.exam.web.dto;

/**
 * Represents a Note data transfer object (DTO)
 * This Record is used to encapsulate Note-related data
 *
 * @param id The ID of a Note
 * @param title The title of a Note
 * @param content The content of a Note
 * @param shareLink The Link to share a Note
 */
public record Note(long id, String title, String content, String shareLink) {
}
