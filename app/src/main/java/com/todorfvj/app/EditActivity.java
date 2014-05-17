package com.todorfvj.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.app.DialogFragment;
import android.app.FragmentManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.todorfvj.listener.AlarmReceiver;
import com.todorfvj.model.StorageHelper;
import com.todorfvj.model.Todo;

import java.util.Calendar;
import com.todorfvj.app.DateTimePicker.DateTimePicker;
import com.todorfvj.app.DateTimePicker.DateTimePickerValue;
import com.todorfvj.app.DateTimePicker.TimePickerFragment;
import com.todorfvj.model.StorageHelper;
import com.todorfvj.model.Todo;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class EditActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
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
        Todo todo;
        StorageHelper store;


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_edit, container, false);

            final Activity act = this.getActivity();
            store = new StorageHelper(this.getActivity());
            Bundle b =  act.getIntent().getExtras();

            final StorageHelper store = new StorageHelper(this.getActivity());

            final Todo todo = store.select(b.getString("todoId"));
            final EditText label = (EditText)rootView.findViewById(R.id.EditText_label) ;
            final EditText content = (EditText)rootView.findViewById(R.id.EditText_content) ;
            final EditText tags = (EditText)rootView.findViewById(R.id.EditText_tags) ;
            final Button changeReminder = (Button)rootView.findViewById(R.id.Button_changeReminder) ;
            final Button save = (Button)rootView.findViewById(R.id.Button_save) ;
            final TextView reminder = (TextView)rootView.findViewById(R.id.TextView_reminder) ;

            label.setText(todo.getLabel());
            content.setText(todo.getContent());
            reminder.setText(todo.getReminder().toString()) ;
            tags.setText(todo.getTags());

            save.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    todo.setLabel(label.getText().toString());
                    todo.setContent(content.getText().toString());
                    todo.setTags(tags.getText().toString());
                    store.update(todo) ;
                }
            });

            changeReminder.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    DateTimePicker dtp = new DateTimePicker(getActivity()) ;
                    dtp.onDateTimeSet(new DateTimePickerValue(){
                        public void onSet(DateTime d){
                            todo.setReminder(d);
                            reminder.setText(d.toString());
                        }
                    }) ;
                    dtp.show() ;
                }
            });



            PendingIntent mAlarmSender;
            mAlarmSender = PendingIntent.getBroadcast(act, 0, new Intent(act.getBaseContext(), AlarmReceiver.class).putExtras(b), 0);

            AlarmManager am = (AlarmManager)act.getSystemService(ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 10);
            am.cancel(mAlarmSender);
            am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),mAlarmSender);

            return rootView;
        }

        public void save(){

        }

    }

}
