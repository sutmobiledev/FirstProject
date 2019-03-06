package com.example.telegram;

import android.util.SparseArray;

import java.util.ArrayList;

public class NotificationCenter {
    public static final int DATA_LOADED = 0x47;
    private static NotificationCenter notificationCenter = new NotificationCenter();
    private SparseArray<ArrayList<Object>> observers = new SparseArray<>();

    private NotificationCenter() {

    }

    public static NotificationCenter getInstance() {
        return notificationCenter;
    }

    public void addObserver(Object observer, int id) {
        ArrayList<Object> objects = observers.get(id);
        if (objects == null) {
            objects = new ArrayList<>();
            observers.put(id, objects);
        }

        if (objects.contains(observer))
            return;

        objects.add(observer);
    }

    public void removeObserver(Object observer) {
        for (int i = 0; i < observers.size(); i++) {
            ArrayList<Object> objects = observers.get(i);
            if (objects != null)
                objects.remove(observer);
        }
    }

    public void data_loaded(int id, Object... args) {
        ArrayList<Object> objects = observers.get(id);

        for (int i = 0; i < objects.size(); i++) {
            ((NotificationCenterDelegate) objects.get(i)).didReceivedNotification(id, args);
        }
    }

    public interface NotificationCenterDelegate {
        void didReceivedNotification(int id, Object... args);
    }
}
