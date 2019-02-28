package com.example.telegram;

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
                ArrayList<Integer> arrayList = MessageStorage.getInstance().load();
                for (int i = 0 ; i < arrayList.size() ; i++)
                    data.add(arrayList.get(i));

            });
        } else {
            dispatchQueue.postRunnable(() -> {

            });

        }
    }
}
