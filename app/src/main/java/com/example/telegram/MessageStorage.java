package com.example.telegram;

import android.util.Log;

public class MessageStorage {
    private static final MessageStorage messageStorage = new MessageStorage();
    public static MessageStorage getInstance(){return messageStorage;}
    private MessageStorage(){

    }

    private DispatchQueue storageQueue = new DispatchQueue("storageQueue");

    public void f1(){
        Log.i("", "MessageStorage::f1");
        storageQueue.postRunnable(() -> Log.i("", "MessageStorage::f1:postRunnable"));
    }
}
