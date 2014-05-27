package com.todorfvj.app.DateTimePicker;

import android.app.Activity;
import android.app.DialogFragment;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.Date;

/**
 * Created by Valentin on 17/05/14.
 */
public class DateTimePicker {

    DateTimePickerValue dtpv = null ;
    DateTime d = new DateTime();
    int year ; int month ; int day ; int hour ; int minute ;
    Activity act ;

    public DateTimePicker(Activity act){this.act = act ;}

    public void onDateTimeSet(DateTimePickerValue dtpv){ this.dtpv = dtpv ; }

    public void show(){
        if(dtpv == null) return ;
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(act.getFragmentManager(), "datePicker");
    }

    public void dateSet(int year, int month, int day){
        this.year = year ; this.month = month+1 ; this.day = day ;
        DialogFragment newFragment = new TimePickerFragment(this);
        newFragment.show(act.getFragmentManager(), "timePicker");
    }

    public void timeSet(int hourOfDay, int minute){
        this.hour = hourOfDay ; this.minute = minute ;
        this.d = new DateTime(year,month,day,hour,minute,0) ;
        this.dtpv.onSet(d) ;
    }

}
