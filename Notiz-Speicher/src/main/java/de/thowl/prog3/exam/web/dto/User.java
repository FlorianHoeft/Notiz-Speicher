package de.thowl.prog3.exam.web.dto;

/**
 * Represents a User data transfer object (DTO)
 * This Record is used to encapsulate user-related data
 *
 * @param id The ID of a User
 * @param username The username of a User
 * @param email The email address of a User
 */
public record User(long id, String username, String email) {
};
