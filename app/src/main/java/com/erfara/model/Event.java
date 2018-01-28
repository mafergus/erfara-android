package com.erfara.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by matthewferguson on 1/11/18.
 */

public class Event {
    final public String id;
    final public String title;
    final public String toBring;
    final public Object[] attendeeIds;
    final public String date;
    final public String startTime;
    final public String endTime;
    final public GeoCoordinate location;
    final public String locationString;
    final public String photo;
    final public String hostId;

    public User host;
    public List<User> attendees;

    final public static class GeoCoordinate {
        final public String address;
        final public String city;
        final public Double latitude;
        final public Double longitude;
        final public String title;

        public GeoCoordinate(String address, String city, Double latitude, Double longitude, String title) {
            this.address = address;
            this.city = city;
            this.latitude = latitude;
            this.longitude = longitude;
            this.title = title;
        }

        static GeoCoordinate fromMap(Map<String, Object> map) {
            return new GeoCoordinate((String)map.get("address"),
                    (String)map.get("city"),
                    (Double)map.get("latitude"),
                    (Double)map.get("longitude"),
                    (String)map.get("title"));
        }
    }

    public Event() {
        this.id = null;
        this.title = null;
        this.toBring = null;
        this.attendeeIds = null;
        this.date = null;
        this.startTime = null;
        this.endTime = null;
        this.locationString = null;
        this.photo = null;
        this.hostId = null;
        this.host = null;
        this.location = null;
    }

    public Event(String id, String title, String toBring, Object[] attendeeIds, String date,
                 String startTime, String endTime, String locationString,
                 String photo, String hostId, User host, GeoCoordinate geoCoord) {
        this.id = id;
        this.title = title;
        this.toBring = toBring;
        this.attendeeIds = attendeeIds;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.locationString = locationString;
        this.photo = photo;
        this.hostId = hostId;
        this.host = host;
        this.location = geoCoord;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", toBring='" + toBring + '\'' +
                ", attendeeIds=" + Arrays.toString(attendeeIds) +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", location=" + location +
                ", locationString='" + locationString + '\'' +
                ", photo='" + photo + '\'' +
                ", hostId='" + hostId + '\'' +
                ", host=" + host +
                ", attendees=" + attendees +
                '}';
    }

    static public Event fromMap(HashMap map) {
        String id = (String)map.get("id");
        String title = (String)map.get("title");
        String toBring = (String)map.get("advices");
        Object[] attendees = ((HashMap)map.get("attendees")).keySet().toArray();
        String date = (String)map.get("date");
        String startTime = (String)map.get("startTime");
        String endTime = (String)map.get("endTime");
        String locationString = (String)map.get("locationString");
        String photo = (String)map.get("photo");
        String host = (String)map.get("userId");
        GeoCoordinate coord = GeoCoordinate.fromMap((Map<String, Object>)map.get("geoCoordinates"));

        return new Event(id, title, toBring, attendees, date, startTime, endTime, locationString, photo, host, null, coord);
    }
}
