package com.example.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.service.TaskService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

        SharedPreferences prefs = getSharedPreferences("com.epsi4android.prefs",0);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putInt("value",42);
        ed.commit();

        Log.d("main", "value: " + prefs.getInt("value",0));
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            /*final TextView txt = (TextView)rootView.findViewById(R.id.textView1);
            txt.setText(R.string.bye_world);

            Button button = (Button)rootView.findViewById(R.id.button);
            button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    txt.setText(R.string.hello_world);
                }
            });*/

            final TextView txt = (TextView)rootView.findViewById(R.id.textView1);
            final EditText ed = (EditText)rootView.findViewById(R.id.editText);
            Button buttonAdd = (Button)rootView.findViewById(R.id.buttonAdd);
            Button buttonDel = (Button)rootView.findViewById(R.id.buttonDel);

            final Activity act = this.getActivity();
            final TaskService service = new TaskService(act);
            ListView lst = (ListView)rootView.findViewById(R.id.listeView);

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this.getActivity(),
                    android.R.layout.simple_list_item_1,
                    service.getAll());
            lst.setAdapter(adapter);

            buttonAdd.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String val = ed.getText().toString();
                    service.add(val);
                    ed.setText(new String());

                    adapter.insert(val, 0);
                    adapter.notifyDataSetChanged();
                }
            });

            buttonDel.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String str = service.removeFirst();
                    Log.d("remove",str);

                    adapter.remove(str);
                    adapter.notifyDataSetChanged();
                }
            });

            return rootView;
        }
    }

}
