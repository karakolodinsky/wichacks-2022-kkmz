package com.tigermeet.api.tigermeetapi.persistence;


import java.io.IOException;
import com.tigermeet.api.tigermeetapi.model.*;

/**
 * defines the interface for User object persistence.
 * 
 * @author kara kolodinsky
 * @author code heavily based on heroes-api by SWEN Faculty
 */
public interface UserDAO {
    /**
     * Retrieves all {@linkplain User user}
     * 
     * @return An array of {@link User user} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * finds all {@linkplain User user} whose name contains the given text.
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link User user} whose names contains the given text; may be empty.
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] findUsers(String containsText) throws IOException;

    /**
     * retrieves an {@linkplain User user} with the given id.
     * 
     * @param id The id of the {@link User user} to get
     * 
     * @return an {@link User user} object with the matching id
     * null if no {@link User user} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id) throws IOException;

    /**
     * creates and saves an {@linkplain User user}.
     * 
     * @param user {@linkplain User user} object to be created and saved
     * The id of the user object is ignored and a new unique id is assigned.
     * Stock is set to whatever the user's current stock is.
     *
     * @return new {@link User user} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;

    /**
     * updates and saves an {@linkplain User user}.
     * 
     * @param {@link User user} object to be updated and saved.
     * 
     * @return an updated {@link User user} if successful, null if
     * {@link User user} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User user) throws IOException;

    /**
     * deletes an {@linkplain User user} with the given id.
     * 
     * @param id the id of the {@link User user}
     * 
     * @return true if the {@link User user} was deleted
     * false if user with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(int id) throws IOException;

    /**
     * checks if an {@linkplain User user}'s name matches any of the {@linkplain User user} names in the current inventory.
     * 
     * @param name the name of the {@link User user}
     * 
     * @return true if the {@link User user}'s name already exists in the current inventory
     * false if it is not found.
     * 
     * @throws IOException if underlying storage cannot be accessed
     */

    boolean nameExists(String name) throws IOException;
}
