package com.erfara;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erfara.erfara.R;
import com.erfara.model.Event;
import com.erfara.model.User;
import com.erfara.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthewferguson on 2/2/18.
 */

public class InfoTabFragment extends Fragment {

    private EventInfoElement description;
    private EventInfoElement time;
    private EventInfoElement location;
    private EventInfoElement toBring;
    private AttendeesList attendees;

    private Event event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.description = getView().findViewById(R.id.description);
        this.time = getView().findViewById(R.id.time);
        this.location = getView().findViewById(R.id.location);
        this.toBring = getView().findViewById(R.id.toBring);
        this.attendees = getView().findViewById(R.id.attendees);

        if (event != null) {
            populate();
        }
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    private void populate() {
        description.setText(event.description);
        location.setText(event.locationString);
        time.setText(Utils.getEventDate(event));
        toBring.setText("To Bring: " + event.toBring.substring(0, 1).toUpperCase() + event.toBring.substring(1));
        if (event.attendeeIds != null) {
            Api.getUsers(event.attendeeIds, userMap -> {
                List<User> users = new ArrayList<>(userMap.values());
                this.attendees.setUsers(users);
            });
        }
    }
}
