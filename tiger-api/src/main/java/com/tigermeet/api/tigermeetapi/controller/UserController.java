package com.tigermeet.api.tigermeetapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Conflict;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.tigermeet.api.tigermeetapi.persistence.*;
import com.tigermeet.api.tigermeetapi.model.*;


/**
 * handles the rest api requests for the user resource.
 * 
 * {@literal @}RestController spring annotation identifies this class as a rest api
 * method handler to the spring framework.
 * 
 * @author kara kolodinsky :o)
 * @author code heavily based on heroes-api by SWEN Faculty
 */

@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDao;

    /**
     * creates a rest api controller to reponds to requests.
     * 
     * @param UserDao the {@link UserDAO item data access object} to perform CRUD operations
     */
    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * responds to the GET request for a {@linkplain User user} for the given id.
     * 
     * @param id The id used to locate the {@link User user}
     * 
     * @return ResponseEntity with {@link User user} object and HTTP status of OK if found
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        LOG.info("GET /users/" + id);
        try {
            User user = userDao.getUser(id);
            if (user != null)
                return new ResponseEntity<User>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain User users}
     * 
     * @return ResponseEntity with array of {@link User user} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<User[]> getUsers() {
        LOG.info("GET /users");
        try{
            User[] users = userDao.getUsers();
            if (users != null)
                return new ResponseEntity<User[]>(users, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * responds to the GET request for all {@linkplain Item items} whose name contains
     * the text in name.
     * 
     * @param name the name parameter which contains the text used to find the {@link Item items}
     * 
     * @return ResponseEntity with array of {@link Item item} objects (may be empty) and
     * HTTP status of OK
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     */
    @GetMapping("/")
    public ResponseEntity<User[]> searchUsers(@RequestParam String name) {
        LOG.info("GET /users/?name= " + name);
        try {
            User[] users = userDao.findUsers(name);
            if (users != null)
                return new ResponseEntity<User[]>(users, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * creates a {@linkplain User user} with the provided user object.
     * 
     * @param user - The {@link User user} to create
     * 
     * @return ResponseEntity with created {@link User user} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link User user} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users/ " + user);
        try {
            User createdUser = userDao.createUser(user);
            if (createdUser != null){
                return new ResponseEntity<User>(createdUser,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<User>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * updates the {@linkplain User user} with the provided {@linkplain User user} object, if it exists.
     * 
     * @param user the {@link User user} to update
     * 
     * @return ResponseEntity with updated {@link User user} object and HTTP status of OK if updated
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("PUT /users " + user);
        try {
            User updatedUser = userDao.updateUser(user);
            if (updatedUser != null)
                return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain User user} with the given id
     * 
     * @param id The id of the {@link User user} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        LOG.info("DELETE /users/" + id);
        try {
            if(userDao.deleteUser(id)) {
                return new ResponseEntity<User>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
