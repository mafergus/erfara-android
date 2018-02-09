package com.erfara;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erfara.erfara.R;
import com.erfara.model.Event;
import com.erfara.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by matthewferguson on 1/10/18.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    public interface ItemClickListener {
        void onItemClick(int position, View v);
    }

    private ItemClickListener itemClickListener;
    private Context context;
    private List<Event> events = new ArrayList<>();

    public EventsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.populate(context, event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public Event getItem(int position) { return events.get(position); }

    public void setEvents(List<Event> events) {
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View rootView;
        private ImageView eventImage;
        private TextView title;
        private TextView subtitle;
        private ImageView hostImage;
        private TextView city;
        private TextView attendeesCount;

        private Event event;

        public ViewHolder(View view) {
            super(view);
            this.rootView = view;
            this.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(EventsAdapter.this.events.indexOf(event), v);
                    }
                }
            });
            this.eventImage = view.findViewById(R.id.image);
            this.title = view.findViewById(R.id.title);
            this.subtitle = view.findViewById(R.id.subtitle);
            this.hostImage = view.findViewById(R.id.userImage);
            this.city = view.findViewById(R.id.city);
            this.attendeesCount = view.findViewById(R.id.attendeeCount);
        }

        @SuppressLint("NewApi")
        public void populate(Context context, Event event) {
            this.event = event;
            this.title.setText(this.event.title);
            this.subtitle.setText(Utils.getEventDate(event));
            Glide.with(context).load(this.event.photo).into(this.eventImage);
            Api.getUser(event.hostId, user -> {
                Glide.with(context).load(user != null ? user.photo : "").into(this.hostImage);
            }, () -> {});
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            String city = "Some City";
            try {
                city = geocoder.getFromLocation(37.4183149, -122.12883449999998, 1).get(0).getLocality();
            } catch (IOException e) {
            }
            this.city.setText(city);
            this.attendeesCount.setText(this.event.attendeeIds.length + "/4");
        }

    }
}
