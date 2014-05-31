package com.todorfvj.app;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.todorfvj.model.Todo;

import java.util.List;

/**
 * Created by julien on 30/04/14.
 */
public class TodoAdapter extends BaseAdapter {
    private List<Todo> data;
    private Context context;
    private OnLongPressListener onLongPressListener;

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
            holder.container = (LinearLayout) convertView.findViewById(R.id.todoItemFront);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.todoItemCheckBox);
            holder.titleView = (TextView) convertView.findViewById(R.id.todoItemTitle);
            holder.titleView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.contentView = (TextView) convertView.findViewById(R.id.todoItemContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(todo.isChecked()){
            holder.titleView.setPaintFlags(holder.titleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.contentView.setPaintFlags(holder.contentView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            holder.titleView.setPaintFlags(holder.titleView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            holder.contentView.setPaintFlags(holder.contentView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("sdf", "Lclick");
                onLongPressListener.onLongPress(todo);
                return false;
            }
        });

        holder.checkbox.setChecked(todo.isChecked());
        holder.titleView.setText(todo.getLabel());
        holder.contentView.setText(todo.getContent());
        return convertView;
    }

    interface OnLongPressListener{
        public void onLongPress(Todo todo);
    }

    /**
     * Listener to be attached to each row
     *
     * @param _listener
     */
    public void setOnLongPress(OnLongPressListener _listener){
        this.onLongPressListener = _listener;
    }
}
