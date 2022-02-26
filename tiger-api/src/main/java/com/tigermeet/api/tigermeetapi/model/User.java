package com.tigermeet.api.tigermeetapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a User entity.
 *
 * @author kara kolodinsky
 * @author code heavily based on heroes-api by SWEN Faculty
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "User [id=%d, name=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("gender") private Gender gender;

    /**
     * create a user with the given id, name, and stock.
     * @param id the id of the user
     * @param name the name of the user
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("id") int id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * retrieves the id of the user
     * @return The id of the user
     */
    public int getId() {return id;}

    /**
     * sets the name of the user; necessary for JSON object to Java object deserialization.
     * @param name The name of the user
     */
    public void setName(String name) {this.name = name;}

    /**
     * retrieves the name of the user
     * @return the name of the user
     */
    public String getName() {return name;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name);
    }
}