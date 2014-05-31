package com.todorfvj.app;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.todorfvj.listener.OnSwipeTouchListener;
import com.todorfvj.model.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentin on 27/05/14.
 */
public class TodoTagAdapter  extends BaseAdapter {

    private ArrayList<String> data;
    private Context context;

    public TodoTagAdapter(Context _context, ArrayList<String> _data) {
        context = _context;
        data = _data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return(data.get(i)) ;
    }

    @Override
    public long getItemId(int i) {
        return i ;
    }

    static class ViewHolder {
        LinearLayout container;
        TextView TextView_tag;
        Button Button_tag;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final String text = data.get(i);
        final ViewGroup p = parent;
        final int nb = i ;
        final ArrayList<String> todoTagList = data ;
        final TodoTagAdapter adapter = this ;


        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.tag_item, parent, false);
            holder = new ViewHolder();
            holder.TextView_tag = (TextView) convertView.findViewById(R.id.TextView_tag);
            holder.Button_tag = (Button) convertView.findViewById(R.id.Button_tag) ;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.TextView_tag.setText(text) ;
        holder.Button_tag.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                todoTagList.remove(nb) ;
                adapter.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
