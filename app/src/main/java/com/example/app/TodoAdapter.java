package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.model.Todo;

import java.util.EventListener;
import java.util.List;

/**
 * Created by julien on 30/04/14.
 */
public class TodoAdapter extends BaseAdapter {
    private List<Todo> data;
    private Context context;
    private OnCheckboxChange listener;

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
        return data.get(i).getId();
    }

    static class ViewHolder {
        CheckBox checkbox;
        TextView titleView;
        TextView contentView;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Todo todo = data.get(i);

        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.todo_item, parent, false);
            holder = new ViewHolder();
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.todoItemCheckBox);
            holder.titleView = (TextView) convertView.findViewById(R.id.todoItemTitle);
            holder.contentView = (TextView) convertView.findViewById(R.id.todoItemContent);

            holder.checkbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    listener.onClick(todo,b);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkbox.setChecked(todo.isChecked());
        holder.titleView.setText(todo.getLabel());
        holder.contentView.setText(todo.getContent());
        return convertView;
    }

    interface OnCheckboxChange{
        public void onClick(Todo todo,boolean b);
    }

    public void setOnCheckboxChange(OnCheckboxChange _listener){
        this.listener = _listener;
    }
}
