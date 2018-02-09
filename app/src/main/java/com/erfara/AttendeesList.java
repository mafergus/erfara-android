package com.erfara;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.erfara.erfara.R;
import com.erfara.model.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by matthewferguson on 2/2/18.
 */

public class AttendeesList extends RelativeLayout {

    private RecyclerView list;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;

    public AttendeesList(Context context) {
        this(context, null);
    }

    public AttendeesList(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AttendeesList(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public AttendeesList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.attendees_list, this, true);
        list = findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        adapter = new Adapter(context);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
    }

    public void setUsers(List<User> users) {
        adapter.setUsers(users);
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        final private Context context;
        final private List<User> users = new ArrayList<>();

        public Adapter(Context context) {
            super();
            this.context = context;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            User user = users.get(position);
            holder.populate(context, user);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendee, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public void setUsers(List<User> users) {
            this.users.clear();
            this.users.addAll(users);
            notifyDataSetChanged();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }

        public void populate(Context context, User user) {
            Glide.with(context).load(user.photo).into(this.image);
        }
    }
}
