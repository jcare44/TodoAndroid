package com.todorfvj.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;
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

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

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
        SwipeListView lst;

        EditText ed ;

        public PlaceholderFragment() {
        }


        @Override
        public void onResume() {
            super.onResume();
            reloadData();
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

            lst = (SwipeListView)rootView.findViewById(R.id.listeView);

            todoList = store.selectAll();

            lst.setSwipeListViewListener(new SwipeListViewListener() {
                @Override
                public void onOpened(int i, boolean b) {
                    //Log.d("sdf","opened"+i+" "+b);
                }

                @Override
                public void onClosed(int i, boolean b) {
                    //Log.d("sdf","closed"+i+" "+b);
                }

                @Override
                public void onListChanged() {
                    //Log.d("sdf","list changed");
                }

                @Override
                public void onMove(int i, float v) {

                }

                @Override
                public void onStartOpen(int i, int i2, boolean b) {

                }

                @Override
                public void onStartClose(int i, boolean b) {

                }

                @Override
                public void onClickFrontView(int i) {
                    Todo todo = todoList.get(i);
                    Log.d("sdf","click");
                    todo.setChecked(!todo.isChecked());
                    store.update(todo);
                    reloadData();
                }

                @Override
                public void onClickBackView(int i) {
                    //Log.d("sdf","backclick");
                }

                @Override
                public void onDismiss(int[] ints) {
                    Log.d("sdf","dissmiss");
                    Todo todo;
                    for(int i=0;i<ints.length;++i)
                    {
                        todo = todoList.get(ints[i]);
                        Log.d("sdf","delete "+todo.getLabel());
                        store.delete(todo);
                        reloadData();
                    }
                }

                @Override
                public int onChangeSwipeMode(int i) {
                    return i;
                }

                @Override
                public void onChoiceChanged(int i, boolean b) {

                }

                @Override
                public void onChoiceStarted() {

                }

                @Override
                public void onChoiceEnded() {

                }

                @Override
                public void onFirstListItem() {

                }

                @Override
                public void onLastListItem() {

                }
            });
            adapter = new TodoAdapter(
                    this.getActivity(),
                    todoList);

            adapter.setOnLongPress(new TodoAdapter.OnLongPressListener() {
                @Override
                public void onLongPress(Todo todo) {
                    Intent intent = new Intent(act, EditActivity.class);
                    Bundle b = new Bundle();
                    b.putString("todoId", todo.getId());
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                }
            });

            lst.setAdapter(adapter);

            buttonAdd.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(ed.getText().length()>0)
                    {
                        Todo todo = new Todo(ed.getText().toString());
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
            lst.resetScrolling();
        }

    }
}
