package com.erfara;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erfara.erfara.R;
import com.erfara.model.Event;
import com.erfara.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getCanonicalName();

    private RecyclerView list;
    private EventsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView userImage;
    private ImageView backgroundImage;
    private TextView username;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        userImage = headerLayout.findViewById(R.id.userImage);
        userImage.setImageResource(R.drawable.erfara_logo);
        backgroundImage = headerLayout.findViewById(R.id.backgroundImage);
        username = headerLayout.findViewById(R.id.username);
        username.setText("Matty Ice");
        email = headerLayout.findViewById(R.id.email);
        email.setText("mattyice@thegreatest.com");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("events");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap> eventsMap = (HashMap) dataSnapshot.getValue();
                List<Event> erfaraEvents = new ArrayList<>();

                for (String key : eventsMap.keySet()) {
                    HashMap eventMap = eventsMap.get(key);
                    eventMap.put("id", key);
                    Event event = Event.fromMap(eventMap);
                    erfaraEvents.add(event);
                }
                Log.v(TAG, "Got events" + Arrays.toString(erfaraEvents.toArray()));
                adapter.setEvents(erfaraEvents);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MY TAG", "Failed to read value.", error.toException());
            }
        });
        Api.populate();

        list = findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        adapter = new EventsAdapter(this);
        adapter.setOnItemClickListener((position, v) -> {
            Intent eventActivityIntent = new Intent(MainActivity.this, EventActivity.class);
            eventActivityIntent.putExtra(Constants.EVENT_ID_KEY, adapter.getItem(position).id);
            startActivity(eventActivityIntent);
        });
        list.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            loadUser(user);
        } else {
            // No user is signed in
        }
    }

    private void loadUser(FirebaseUser user) {
        this.username.setText(user.getDisplayName());
        this.email.setText(user.getEmail());
        String uid = user.getUid();
        Api.getUser(uid, theUser -> {
            this.username.setText(theUser.name);
            this.email.setText(theUser.email);
            Glide.with(MainActivity.this).load(theUser.photo).into(this.userImage);
            Glide.with(MainActivity.this).load(theUser.coverPhoto).into(this.backgroundImage);
        }, () -> {});
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Log Out");
            builder.setMessage("Are you sure you want to log out?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            });
            AlertDialog dialog = builder.create();
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", ((dialogInterface, i) -> {
                dialog.dismiss();
            }));
            builder.create().show();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
