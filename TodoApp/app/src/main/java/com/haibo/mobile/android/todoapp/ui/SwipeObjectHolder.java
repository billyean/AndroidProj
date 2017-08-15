package com.haibo.mobile.android.todoapp.ui;

/**
 * Created by hyan on 8/14/17.
 */

public class SwipeObjectHolder<T> {
    private T t;

    private boolean showing;

    public SwipeObjectHolder(T object) {
        t = object;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public boolean isShowing() {
        return showing;
    }

    public T getHeldObject() {
        return t;
    }
}
