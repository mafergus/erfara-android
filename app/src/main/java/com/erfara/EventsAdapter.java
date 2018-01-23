package com.erfara;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.erfara.erfara.R;
import com.erfara.model.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by matthewferguson on 1/10/18.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
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

    public void setEvents(List<Event> events) {
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
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
//                    Intent intent = new Intent(context, CauseDetailActivity.class);
//                    String objectJson = new GsonBuilder().create().toJson(cause);
//                    intent.putExtra("cause", objectJson);
//                    context.startActivity(intent);
                    Toast.makeText(rootView.getContext(), "TESTING", Toast.LENGTH_LONG).show();
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
            DateFormat iso8601format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            DateFormat df = DateFormat.getPatternInstance(DateFormat.ABBR_MONTH_WEEKDAY_DAY);
            DateFormat tf = DateFormat.getPatternInstance(DateFormat.HOUR_MINUTE);
            Date date;
            Date time;
            try {
                date = iso8601format.parse(this.event.startTime);
                time = iso8601format.parse(this.event.startTime);
            } catch (ParseException e) {
                date = new Date();
                time = new Date();
            }
            this.subtitle.setText(tf.format(time) + " " + df.format(date));
            Glide.with(context).load(this.event.photo).into(this.eventImage);
            Map<String, HashMap> userMap = (HashMap)Api.objects.get("users");
            Map<String, Object> user = userMap.get(event.hostId);
            Glide.with(context).load(user != null ? user.get("photo") : "").into(this.hostImage);

//            Picasso.with(context).load(cause.leadingImage).into(this.causeImage);
//            this.title.setText(cause.title);
//            this.text.setText(cause.detail);
        }

    }
}
