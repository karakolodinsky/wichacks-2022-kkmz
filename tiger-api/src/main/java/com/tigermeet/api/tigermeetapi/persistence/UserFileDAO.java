package com.tigermeet.api.tigermeetapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tigermeet.api.tigermeetapi.model.*;


/**
 * Implements the functionality for JSON file-based peristance for users
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author kara kolodinsky
 * @author code heavily based on heroes-api by SWEN Faculty
 */
@Component
public class UserFileDAO implements UserDAO {
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<Integer,User> users;   // Provides a local cache of the user objects
    private ObjectMapper objectMapper;  // Provides conversion between user
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new user
    private String filename;    // Filename to read from and write to

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${userinfo.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the items from the file
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    /**
     * generates an array of {@linkplain Item items} from the tree map for any
     * {@linkplain Item items} that contains the text specified by containsText.
     * if containsText is null, the array contains all of the {@linkplain Item items}
     * in the tree map
     * 
     * @return the array of {@link Item items}, may be empty.
     */
    private User[] getUsersArray(String containsText) { // if containsText == null, no filter
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (containsText == null || user.getName().contains(containsText)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    /**
     * saves the {@linkplain User users} from the map into the file as an array of JSON objects.
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    /**
     * loads {@linkplain User user} from the JSON file into the map;
     * also sets next id to one more than the greatest id found in the file.
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        // deserializes the JSON objects from the file into an array of items;
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file.
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        // add each user to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getId(),user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }
        // make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User[] getUsers() {
        synchronized(users) {
            return getUsersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User[] findUsers(String containsText) {
        synchronized(users) {
            return getUsersArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User getUser(int id) {
        synchronized(users) {
            if (users.containsKey(id))
                return users.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized(users) {
            // we create a new item object because the id field is immutable
            // and we need to assign the next unique id
            if (nameExists(user.getName())== false){
                User newUser = new User(nextId(),user.getName(),user.getGender(), user.getSeeking(), user.getBirthday(), user.getDeaf(), user.getEatery(), user.getStudy() );
                users.put(newUser.getId(),newUser);
                save(); // may throw an IOException
                return newUser;
            }
            else {
                return null;
            }
            
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public User updateUser(User user) throws IOException {
        synchronized(users) {
            if (users.containsKey(user.getId()) == false)
                return null;  // user does not exist

            users.put(user.getId(),user);
            save(); // may throw an IOException
            return user;
        }
    }

   /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteUser(int id) throws IOException {
        synchronized(users) {
            if (users.containsKey(id)) {
                users.remove(id);
                return save();
            }
            else
                return false;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean nameExists(String name) throws IOException {
    synchronized(users) {
        for (User current_user: users.values()){
            if (current_user.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
}
