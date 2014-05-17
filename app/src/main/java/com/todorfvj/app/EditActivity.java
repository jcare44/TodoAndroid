package com.todorfvj.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.todorfvj.listener.AlarmReceiver;
import com.todorfvj.model.StorageHelper;
import com.todorfvj.model.Todo;

import java.util.Calendar;
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
            todo = store.select(b.getString("todoId"));

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
    }

}
