package com.erfara.model;

import java.util.HashMap;

/**
 * Created by matthewferguson on 1/12/18.
 */

final public class User {
    final public String birthday;
    final public HashMap skills;
    final public String uid;
    final public String hometown;
    final public HashMap attending;
    final public String coverPhoto;
    final public String name;
    final public String photo;
    final public String location;
    final public String email;
    final public HashMap hosting;

    public User(String birthday, HashMap skills, String uid, String hometown,
                HashMap attending, String coverPhoto, String name,
                String photo, String location, String email, HashMap hosting) {
        this.birthday = birthday;
        this.skills = skills;
        this.uid = uid;
        this.hometown = hometown;
        this.attending = attending;
        this.coverPhoto = coverPhoto;
        this.name = name;
        this.photo = photo;
        this.location = location;
        this.email = email;
        this.hosting = hosting;
    }

    static public User fromMap(HashMap map) {
        String birthday = (String)map.get("birthday");
        HashMap skills = (HashMap)map.get("skills");
        String uid = (String)map.get("uid");
        String hometown = (String)map.get("hometown");
        HashMap attending = (HashMap)map.get("attending");
        String coverPhoto = (String)map.get("coverPhoto");
        String name = (String)map.get("name");
        String photo = (String)map.get("photo");
        String location = (String)map.get("location");
        String email = (String)map.get("email");
        HashMap hosting = (HashMap)map.get("events");

        return new User(birthday, skills, uid, hometown, attending, coverPhoto, name, photo, location, email, hosting);
    }
}
