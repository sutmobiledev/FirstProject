package com.example.telegram;

import android.os.Message;

import java.util.ArrayList;

public class MessageController {
    private static final MessageController messageControllerInstance = new MessageController();
    private ArrayList<Post> data;
    private DispatchQueue storageQueue, cloudQueue;


    private MessageController() {
        cloudQueue = new DispatchQueue("Cloud") {
            @Override
            public void handleMessage(Message inputMessage) {
                if (inputMessage.what == NotificationCenter.DATA_LOADED) {
//                    storageQueue.postRunnable(() -> StorageManager.getInstance().save_file(data.get(data.size() - 1)));
                    storageQueue.postRunnable(() -> StorageManager.getInstance().savePostsToDB(data));

                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj);
                }
            }
        };
        storageQueue = new DispatchQueue("Storage") {
            @Override
            public void handleMessage(Message inputMessage) {
                if (inputMessage.what == NotificationCenter.DATA_LOADED) {
                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj);
                }
            }
        };
        data = new ArrayList<>();
    }

    public static MessageController getInstance() {
        return messageControllerInstance;
    }

    public ArrayList<Post> getData() {
        return data;
    }


    public void fetch(boolean fromCache, Integer param) {
        if (fromCache) {
            // refresh btn
            storageQueue.postRunnable(() -> {
//                ArrayList<Integer> arrayList = StorageManager.getInstance().loadPosts(param);
//                data.addAll(arrayList);
                data = StorageManager.getInstance().loadPostsFromDB();

                Message message = new Message();
                message.what = NotificationCenter.DATA_LOADED;
                message.obj = data;
                storageQueue.sendMessage(message, 0);
            });
        } else {
            // get btn
            cloudQueue.postRunnable(() -> {
//                ArrayList<Integer> arrayList = new ArrayList<>();
//                arrayList.add(1);
                data = ConnectionManager.getInstance().loadPosts();
                //data.addAll(arrayList);

                Message message = new Message();
                message.what = NotificationCenter.DATA_LOADED;
                message.obj = data;
                cloudQueue.sendMessage(message, 0);
            });
        }
    }
}
