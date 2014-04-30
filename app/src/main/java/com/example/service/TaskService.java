package com.example.service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by julien on 01/04/14.
 */
public class TaskService
{
    private Activity activity;

    public TaskService(Activity act)
    {
        activity = act;
    }

    public ArrayList<String> getAll()
    {
        SharedPreferences prefs = activity.getSharedPreferences("todo",0);
        String todo = prefs.getString("todolist","sample");
        Log.d("Data",todo);

        ArrayList<String> dataList = new ArrayList<String>(Arrays.asList(todo.split(";")));

        return dataList;
    }

    public void add(String elem)
    {
        SharedPreferences prefs = activity.getSharedPreferences("todo",0);
        String todo = prefs.getString("todolist","sample");

        todo = elem+";"+todo;

        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString("todolist", todo);
        prefsEdit.commit();
    }

    public String removeFirst()
    {
        SharedPreferences prefs = activity.getSharedPreferences("todo",0);
        String todo = prefs.getString("todolist","sample");

        String[] aTodo = todo.split(";",2);

        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString("todolist", aTodo[1]);
        prefsEdit.commit();

        return aTodo[0];
    }
}
