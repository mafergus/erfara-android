package com.erfara;

import android.util.Log;

import com.erfara.model.Event;
import com.erfara.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthewferguson on 1/12/18.
 */

public class Api {

    final public static Map<String, Object> objects = new HashMap<>();

    public interface DataCallback<T> {
        void onReceived(T obj);
    }

    public interface ErrorCallback {
        void onError();
    }

    static void populate() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d("MY TAG", "Value is: ");
                HashMap<String, HashMap> dbMap = (HashMap) dataSnapshot.getValue();
                for (String key : dbMap.keySet()) {
                    Api.objects.put(key, dbMap.get(key));
                }
                int x = 0;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MY TAG", "Failed to read value.", error.toException());
            }
        });
    }

    static void getEvent(String eventId, final DataCallback<Event> callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("events");
        Query query = reference.orderByKey().equalTo(eventId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap<String, Object> eventMap = (HashMap<String, Object>) ((HashMap) dataSnapshot.getValue()).get(eventId);
                    eventMap.put("id", eventId);
                    callback.onReceived(Event.fromMap(eventMap));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    static User getUser(final String userId, final DataCallback<User> userCallback, final ErrorCallback errorCallback) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query query = reference.orderByKey().equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap<String, Object> userMap = (HashMap<String, Object>) ((HashMap) dataSnapshot.getValue()).get(userId);
                    User user = User.fromMap(userMap);
                    userCallback.onReceived(user);
                } else {
                    userCallback.onReceived(User.nullUser());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                errorCallback.onError();
            }
        });

        return null;
    }

    static void getUsers(final Object[] userIds, final DataCallback<Map<String, User>> callback) {
        final Map<String, User> users = new HashMap<>();
        for (Object o : userIds) {
            String id = (String) o;
            Api.getUser(id, user -> {
                users.put(user.uid, user);
                if (users.size() == userIds.length) {
                    callback.onReceived(users);
                }
            }, () -> {

            });
        }
    }
}
