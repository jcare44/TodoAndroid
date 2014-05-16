package com.todorfvj.app;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final Activity act = this.getActivity();
            store = new StorageHelper(this.getActivity());
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final TextView txt = (TextView)rootView.findViewById(R.id.textView1);
            final EditText ed = (EditText)rootView.findViewById(R.id.editText);
            Button buttonAdd = (Button)rootView.findViewById(R.id.buttonAdd);

            ListView lst = (ListView)rootView.findViewById(R.id.listeView);

            todoList = store.selectAll();

            adapter = new TodoAdapter(
                    this.getActivity(),
                    todoList);

            adapter.setOnCheckboxChange(new TodoAdapter.OnCheckboxClick(){
                @Override
                public void onClick(Todo todo) {
                    store.update(todo);
                }
            });

            lst.setAdapter(adapter);

            buttonAdd.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String val = ed.getText().toString();
                    Todo todo = new Todo(val, "", false, new Date(), "") ;
                    Logger.getAnonymousLogger().warning(todo.toString());
                    store.insert(todo);
                    ed.setText("");
                    reloadData();
                }
            });

            /*buttonDel.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String str = store.
                    Log.d("remove",str);

                    adapter.remove(str);
                    adapter.notifyDataSetChanged();
                }
            });*/

            return rootView;
        }

        public void reloadData() {
            todoList.clear();
            todoList.addAll(store.selectAll());
            adapter.notifyDataSetChanged();
        }

    }
}
