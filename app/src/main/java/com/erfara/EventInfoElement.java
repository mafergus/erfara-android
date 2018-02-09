package com.erfara;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erfara.erfara.R;

/**
 * Created by matthewferguson on 2/2/18.
 */

public class EventInfoElement extends RelativeLayout {

    private ImageView icon;
    private TextView text;

    public EventInfoElement(Context context) {
        this(context, null);
    }

    public EventInfoElement(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public EventInfoElement(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public EventInfoElement(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.item_event_info, this, true);
        icon = findViewById(R.id.icon);
        text = findViewById(R.id.text);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EventInfoElement );
        final int N = a.getIndexCount();
        Drawable icon;
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.EventInfoElement_icon:
                    icon = a.getDrawable(attr);
                    setIcon(icon);
                    break;
            }
        }
        a.recycle();
    }

    public void setIcon(int resId) { this.icon.setImageResource(resId); }

    public void setIcon(Drawable d) { this.icon.setImageDrawable(d); }

    public void setText(CharSequence text) { this.text.setText(text); }
}
