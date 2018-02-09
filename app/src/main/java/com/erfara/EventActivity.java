package com.erfara;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erfara.erfara.R;
import com.erfara.model.Event;
import com.erfara.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthewferguson on 1/28/18.
 */

public class EventActivity extends AppCompatActivity {
    public final static String TAG = EventActivity.class.getCanonicalName();
    private String eventId;
    private Event event;

    private ImageView heroImage;
    private TextView title;
    private ImageView hostImage;
    private TextView hostname;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private InfoTabFragment infoTabFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventId = getIntent().getStringExtra(Constants.EVENT_ID_KEY);
        Api.getEvent(eventId, event -> {
            this.event = event;
            infoTabFragment.setEvent(event);

            title.setText(event.title);
            Api.getUser(event.hostId, user -> {
                Glide.with(EventActivity.this).load(user != null ? user.photo : "").into(hostImage);
                hostname.setText(user.name);
            }, () -> {});
            Glide.with(EventActivity.this).load(event != null ? event.photo : "").into(heroImage);
        });

        Log.v(TAG, "Got event id: " + eventId);

        heroImage = findViewById(R.id.heroImage);
        title = findViewById(R.id.title);
        hostImage = findViewById(R.id.hostImage);
        hostname = findViewById(R.id.hostname);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        infoTabFragment = new InfoTabFragment();
        adapter.addFragment(infoTabFragment, "INFO");
        adapter.addFragment(new InfoTabFragment(), "DISCUSSION");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
