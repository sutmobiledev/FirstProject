package com.example.telegram;

import android.os.Message;

import java.util.ArrayList;

public class MessageController {
    private static final MessageController ourInstance = new MessageController();
    private ArrayList<Integer> data;
    private DispatchQueue dispatchQueue;
    private StorageManager storageManager;


    public static MessageController getInstance() {
        return ourInstance;
    }

    private MessageController() {
        dispatchQueue = new DispatchQueue("GetMessages"){
            @Override
            public void handleMessage(Message inputMessage) {
                if (inputMessage.what == NotificationCenter.DATA_LOADED){
                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj);
                }
            }
        };
        data = new ArrayList<>();
        storageManager = new StorageManager();
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
                ArrayList<Integer> arrayList = storageManager.load();
                data.addAll(arrayList);

                Message message = new Message();
                message.what = NotificationCenter.DATA_LOADED;
                message.obj = getData();
                dispatchQueue.sendMessage(message, 0);
            });
        } else {
            dispatchQueue.postRunnable(() -> {
                ArrayList<Integer> arrayList;

                if (param == null)
                    arrayList = ConnectionManager.getInstance().load(0);
                else
                    arrayList = ConnectionManager.getInstance().load(param);

                storageManager.save(arrayList.get(arrayList.size()-1));
                data.addAll(arrayList);

                Message message = new Message();
                message.what = NotificationCenter.DATA_LOADED;
                message.obj = getData();
                dispatchQueue.sendMessage(message, 0);
            });

        }
    }
}
