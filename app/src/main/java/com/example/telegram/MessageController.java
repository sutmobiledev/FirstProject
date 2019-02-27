package com.example.telegram;

import java.util.ArrayList;

public class MessageController {
    private static final MessageController ourInstance = new MessageController();
    private ArrayList<Integer> data;
    private DispatchQueue dispatchQueue = new DispatchQueue("What");


    public static MessageController getInstance() {
        return ourInstance;
    }

    private MessageController() {
        data = new ArrayList<>();
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }

    public DispatchQueue getDispatchQueue() {
        return dispatchQueue;
    }

    public void setDispatchQueue(DispatchQueue dispatchQueue) {
        this.dispatchQueue = dispatchQueue;
    }

    public void fetch(boolean fromCache) {
        if (fromCache == true) {
            dispatchQueue.postRunnable(new Runnable() {
                @Override
                public void run() {

                }
            });
        } else {
            dispatchQueue.postRunnable(new Runnable() {
                @Override
                public void run() {

                }

            });

        }
    }

    public void onComplite() {

    }

    public void doJob() {

    }
}
