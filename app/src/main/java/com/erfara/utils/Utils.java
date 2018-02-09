package com.erfara.utils;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.erfara.model.Event;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by matthewferguson on 1/22/18.
 */

public class Utils {
    static public String getEventDate(Event event) {
        DateFormat iso8601format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        DateFormat df = DateFormat.getPatternInstance(DateFormat.ABBR_MONTH_WEEKDAY_DAY);
        DateFormat tf = DateFormat.getPatternInstance(DateFormat.HOUR_MINUTE);
        Date date;
        Date time;
        try {
            date = iso8601format.parse(event.startTime);
            time = iso8601format.parse(event.startTime);
        } catch (ParseException e) {
            date = new Date();
            time = new Date();
        }
        return tf.format(time) + " " + df.format(date);
    }
}
