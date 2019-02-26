package com.example.telegram;

import android.util.SparseArray;

import java.util.ArrayList;

public class NotificationCenter {
    private NotificationCenter notificationCenter;
    private SparseArray<ArrayList<Object>> observers = new SparseArray<>();

    private NotificationCenter(){

    }

    public NotificationCenter getInstance(){
        if (notificationCenter == null)
            notificationCenter = new NotificationCenter();

        return notificationCenter;
    }

    public void add(NotifCenterCallback notifCenterCallback, int id){
        ArrayList<Object> objects = observers.get(id);
        if (objects == null)
            observers.put(id, new ArrayList<Object>());
        if (objects.contains(notifCenterCallback))
            return;

        objects.add(notifCenterCallback);
    }

    public void post(int id, Object result){
        ArrayList<Object> objects = observers.get(id);

        for (int i = 0; i < objects.size() ; i++) {
            ((NotifCenterCallback)objects.get(i)).run(id, result);
        }
    }
}
