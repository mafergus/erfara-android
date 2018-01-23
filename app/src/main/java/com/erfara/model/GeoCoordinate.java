package com.erfara.model;

/**
 * Created by matthewferguson on 1/12/18.
 */

final public class GeoCoordinate {
    final public String address;
    final public String city;
    final public Float latitude;
    final public Float longitude;
    final public String title;

    public GeoCoordinate() {
        this.address = null;
        this.city = null;
        this.latitude = null;
        this.longitude = null;
        this.title = null;
    }

    public GeoCoordinate(String address, String city, Float latitude, Float longitude, String title) {
        this.address = address;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }
}
