package com.tigermeet.api.tigermeetapi.model;

public enum EateryLocation {
    ARTESANO_BAKERY_AND_CAFE("Artesano Bakey & Cafe", "Monroe Hall, 1 Lomb Memorial Dr, Rochester, NY 14623");

    private String restaurant;
    private String location;

    private EateryLocation(String restaurant, String location){
        this.restaurant = restaurant;
        this.location = location;
    }
}
