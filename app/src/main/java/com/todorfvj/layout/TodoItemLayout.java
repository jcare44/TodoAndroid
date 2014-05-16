package com.todorfvj.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by julien on 16/05/14.
 */
public class TodoItemLayout extends LinearLayout {
    public TodoItemLayout(Context ctx){
        super(ctx);
    }

    public TodoItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TodoItemLayout(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true; // With this i tell my layout to consume all the touch events from its childs
    }
}
