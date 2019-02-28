package com.example.telegram;

import android.os.Message;

import java.util.ArrayList;

public class MessageController {
    private static final MessageController ourInstance = new MessageController();
    private ArrayList<Integer> data;
    private DispatchQueue dispatchQueue;


    public static MessageController getInstance() {
        return ourInstance;
    }

    private MessageController() {
        dispatchQueue = new DispatchQueue("GetMessages");
        data = new ArrayList<>();
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }

    public void fetch(boolean fromCache, Integer param) {
        if (fromCache) {
            dispatchQueue.postRunnable(() -> {
                ArrayList<Integer> arrayList = StorageManager.getInstance().load();
                data.addAll(arrayList);

                Message message = new Message();
                message.what = NotificationCenter.DATA_LOADED;
                message.obj = getData();
                dispatchQueue.sendMessage(message, 0);
            });
        } else {
            dispatchQueue.postRunnable(() -> {
                ArrayList<Integer> arrayList = ConnectionManager.getInstance().load(param);
                data.addAll(arrayList);

                Message message = new Message();
                message.what = NotificationCenter.DATA_LOADED;
                message.obj = getData();
                dispatchQueue.sendMessage(message, 0);
            });

        }
    }
}
