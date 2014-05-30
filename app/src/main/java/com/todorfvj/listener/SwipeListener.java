package com.todorfvj.listener;

import com.fortysevendeg.swipelistview.SwipeListViewListener;
import com.todorfvj.model.Todo;

import java.util.List;

/**
 * Created by julien on 30/05/14.
 */
public class SwipeListener implements SwipeListViewListener {
    List<Todo> todoList;

    SwipeListener(List<Todo> _todoList) {
        todoList = _todoList;
    }

    @Override
    public void onOpened(int i, boolean b) {

    }

    @Override
    public void onClosed(int i, boolean b) {

    }

    @Override
    public void onListChanged() {

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

    }

    @Override
    public void onClickBackView(int i) {

    }

    @Override
    public void onDismiss(int[] ints) {

    }

    @Override
    public int onChangeSwipeMode(int i) {
        return 0;
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
}
