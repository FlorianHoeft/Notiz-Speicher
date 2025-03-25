package de.thowl.prog3.exam.web.dto;

/**
 * Represents a Category data transfer object (DTO)
 * This Record is used to encapsulate Category-related data
 *
 * @param id The ID of a Category
 * @param name The name of a Category
 */
public record Category(long id, String name) {
}
