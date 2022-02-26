package com.tigermeet.api.tigermeetapi.model;

/**
 * The gender of a user.
 * @author zmb6893
 */

public enum Gender {
    FEMALE("Female"),
    MALE("Male"),
    NONBINARY("Nonbinary");

    private String gender;

    private Gender(String gender){
        this.gender = gender;
    }

    @Override
    public String toString() {
        return this.gender;
    }
}