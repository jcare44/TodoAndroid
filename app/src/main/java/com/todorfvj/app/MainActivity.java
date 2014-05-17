package com.todorfvj.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.todorfvj.model.StorageHelper;
import com.todorfvj.model.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        List<Todo> todoList;
        TodoAdapter adapter;
        StorageHelper store;

        EditText ed ;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final Activity act = this.getActivity();
            store = new StorageHelper(this.getActivity());
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final EditText ed = (EditText)rootView.findViewById(R.id.editText);
            this.ed = ed ;
            Button buttonAdd = (Button)rootView.findViewById(R.id.buttonAdd);

            ListView lst = (ListView)rootView.findViewById(R.id.listeView);

            todoList = store.selectAll();

            adapter = new TodoAdapter(
                    this.getActivity(),
                    todoList);

            adapter.setOnCheckboxChange(new TodoAdapter.OnCheckboxClickListener(){
                @Override
                public void onClick(Todo todo) {
                    store.update(todo);
                    reloadData() ;
                }
            });

            adapter.setOnSwipe(new TodoAdapter.OnSwipeListener() {
                @Override
                public void onSwipe(Todo todo) {
                    Log.d("sdf", todo.getLabel());
                    store.delete(todo);
                    reloadData();
                }
            });

            adapter.setOnLongPress(new TodoAdapter.OnLongPressListener() {
                @Override
                public void onLongPress(Todo todo) {
                    Intent intent = new Intent(act, EditActivity.class);
                    Bundle b = new Bundle();
                    b.putString("todoId", todo.getId());
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    act.finish();
                }
            });

            lst.setAdapter(adapter);

            buttonAdd.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(ed.getText().length()>0)
                    {
                        Todo todo = new Todo(ed.getText().toString(), "", false, new Date(), "") ;
                        store.insert(todo);
                        ed.setText("");
                        reloadData();
                    }
                }
            });

            TextWatcher tw = new TextWatcher() {
                public void afterTextChanged(Editable s){}
                public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                public void onTextChanged (CharSequence s, int start, int before,int count) {
                    reloadData();
                }
            };

            ed.addTextChangedListener(tw);
            return rootView;
        }

        public void reloadData() {
            todoList.clear();
            todoList.addAll(store.selectAll(this.ed.getText().toString()));
            adapter.notifyDataSetChanged();
        }

    }
}
