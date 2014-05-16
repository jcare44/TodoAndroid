package com.todorfvj.app;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.todorfvj.listener.OnSwipeTouchListener;
import com.todorfvj.model.Todo;

import java.util.List;

/**
 * Created by julien on 30/04/14.
 */
public class TodoAdapter extends BaseAdapter {
    private List<Todo> data;
    private Context context;
    private OnCheckboxClickListener onClickListener;
    private OnSwipeListener onSwipeListener;

    public TodoAdapter(Context _context, List<Todo> _data) {
        context = _context;
        data = _data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        LinearLayout container;
        CheckBox checkbox;
        TextView titleView;
        TextView contentView;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Todo todo = data.get(i);
        final ViewGroup p = parent;

        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.todo_item, parent, false);
            holder = new ViewHolder();
            holder.container = (LinearLayout) convertView.findViewById(R.id.todoItem);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.todoItemCheckBox);
            holder.titleView = (TextView) convertView.findViewById(R.id.todoItemTitle);
            holder.contentView = (TextView) convertView.findViewById(R.id.todoItemContent);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.container.setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public void onSwipeRight() {
                Log.d("sdf","right");
                onSwipeListener.onSwipe(todo);
            }
            @Override
            public void onSwipeLeft() {
                Log.d("sdf","left");
                onSwipeListener.onSwipe(todo);
            }
            @Override
            public void onClick() {
                Log.d("sdf","click");
                todo.setChecked(!todo.isChecked());
                onClickListener.onClick(todo);
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        holder.container.setBackgroundColor(todo.isChecked() ? Color.argb(50,18,120,0) : Color.TRANSPARENT) ;
        holder.checkbox.setChecked(todo.isChecked());
        holder.titleView.setText(todo.getLabel());
        holder.contentView.setText(todo.getContent());
        return convertView;
    }

    interface OnCheckboxClickListener{
        public void onClick(Todo todo);
    }

    /**
     * Listener to be attached to each checkbox
     *
     * @param _listener
     */
    public void setOnCheckboxChange(OnCheckboxClickListener _listener){
        this.onClickListener = _listener;
    }

    interface OnSwipeListener{
        public void onSwipe(Todo todo);
    }

    /**
     * Listener to be attached to each row
     * Do not work so well
     *
     * @param _listener
     */
    public void setOnSwipe(OnSwipeListener _listener){
        this.onSwipeListener = _listener;
    }
}
