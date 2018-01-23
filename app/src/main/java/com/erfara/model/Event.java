package com.erfara.model;

import java.util.HashMap;
import java.util.List;

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
//    final public GeoCoordinate location;
    final public String locationString;
    final public String photo;
    final public String hostId;

    public User host;
    public List<User> attendees;

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
    }

    public Event(String id, String title, String toBring, Object[] attendeeIds, String date,
                 String startTime, String endTime, String locationString,
                 String photo, String hostId, User host) {
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

        return new Event(id, title, toBring, attendees, date, startTime, endTime, locationString, photo, host, null);
    }
}
