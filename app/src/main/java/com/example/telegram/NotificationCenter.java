package com.example.telegram;

import android.support.annotation.UiThread;
import android.util.SparseArray;

import java.util.ArrayList;

public class NotificationCenter {
    private static final int DATA_LOADED = 0x47;

    public interface NotificationCenterDelegate {
        void didReceivedNotification(int id, Object... args);
    }

    private NotificationCenter notificationCenter = new NotificationCenter();
    private SparseArray<ArrayList<Object>> observers = new SparseArray<>();

    private NotificationCenter(){

    }

    @UiThread
    public NotificationCenter getInstance(){
        return notificationCenter;
    }

    public void addObserver(Object observer, int id){
        ArrayList<Object> objects = observers.get(id);
        if (objects == null){
            objects = new ArrayList<Object>();
            observers.put(id, objects);
        }

        if (objects.contains(observer))
            return;

        objects.add(observer);
    }

    public void removeObserver(Object observer){
        for (int i = 0 ; i < observers.size() ; i++){
            ArrayList<Object> objects = observers.get(i);
            if (objects != null)
                objects.remove(observer);
        }
    }

    @UiThread
    public void post(int id, Object... args){
        ArrayList<Object> objects = observers.get(id);

        for (int i = 0; i < objects.size() ; i++) {
            ((NotificationCenterDelegate)objects.get(i)).didReceivedNotification(id, args);
        }
    }
}
