package com.example.telegram;

import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

public class MessageController {
    private static final MessageController messageControllerInstance = new MessageController();
    private final int updatePeriod = 5 * 60 * 1000;
    private long lastPostUpdate, lastCommentsUpdate;
    private DispatchQueue storageQueue, cloudQueue;

    private ArrayList<Post> postArrayList;
    private ArrayList<Comment> commentArrayList;

    private MessageController() {
        cloudQueue = new DispatchQueue("Cloud") {
            @Override
            public void handleMessage(Message inputMessage) {
                if (inputMessage.what == NotificationCenter.POST_LOADED) {
                    storageQueue.postRunnable(() -> StorageManager.getInstance().savePostsToDB(postArrayList));

                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj, inputMessage.arg1);
                } else if (inputMessage.what == NotificationCenter.COMMENT_LOADED) {
                    storageQueue.postRunnable(() -> StorageManager.getInstance().saveCommentsToDB(commentArrayList));

                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj, inputMessage.arg1);
                }
            }
        };
        storageQueue = new DispatchQueue("Storage") {
            @Override
            public void handleMessage(Message inputMessage) {
                if (inputMessage.what == NotificationCenter.POST_LOADED) {
                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj, inputMessage.arg1);
                } else if (inputMessage.what == NotificationCenter.COMMENT_LOADED) {
                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj, inputMessage.arg1);
                }
            }
        };

        postArrayList = new ArrayList<>();
        commentArrayList = new ArrayList<>();

        lastPostUpdate = 0;
        lastCommentsUpdate = 0;
    }

    public static MessageController getInstance() {
        return messageControllerInstance;
    }

    public void fetchPost(boolean mustLoadFromDB) {
        if (mustLoadFromDB) {
            storageQueue.postRunnable(() -> {
                postArrayList = StorageManager.getInstance().loadPostsFromDB();

                Message message = new Message();
                message.what = NotificationCenter.POST_LOADED;
                message.obj = postArrayList;
                message.arg1 = 1;
                storageQueue.sendMessage(message, 0);
            });
            return;
        }

        if (System.currentTimeMillis() - lastPostUpdate > updatePeriod) {
            lastPostUpdate = System.currentTimeMillis();

            cloudQueue.postRunnable(() -> {
                postArrayList = ConnectionManager.getInstance().loadPosts();

                Message message = new Message();
                message.what = NotificationCenter.POST_LOADED;
                message.obj = postArrayList;
                message.arg1 = 0;
                cloudQueue.sendMessage(message, 0);
            });
        } else {
            storageQueue.postRunnable(() -> {
                ArrayList<Post> posts = StorageManager.getInstance().loadPostsFromDB();
                if (posts != null) {
                    postArrayList = posts;

                    Message message = new Message();
                    message.what = NotificationCenter.POST_LOADED;
                    message.obj = postArrayList;
                    message.arg1 = 0;
                    storageQueue.sendMessage(message, 0);
                } else {
                    cloudQueue.postRunnable(() -> {
                        postArrayList = ConnectionManager.getInstance().loadPosts();

                        Message message = new Message();
                        message.what = NotificationCenter.POST_LOADED;
                        message.obj = postArrayList;
                        message.arg1 = 0;
                        cloudQueue.sendMessage(message, 0);
                    });
                }
            });
        }
    }

    public void fetchComments(int postId, boolean mustLoadFromDB) {
        if (mustLoadFromDB) {
            storageQueue.postRunnable(() -> {
                commentArrayList = StorageManager.getInstance().loadCommentsFromDB(postId);

                Message message = new Message();
                message.what = NotificationCenter.COMMENT_LOADED;
                message.obj = commentArrayList;
                message.arg1 = 1;
                storageQueue.sendMessage(message, 0);
            });
            return;
        }

        if (System.currentTimeMillis() - lastCommentsUpdate > updatePeriod) {
            lastCommentsUpdate = System.currentTimeMillis();

            cloudQueue.postRunnable(() -> {
                commentArrayList = ConnectionManager.getInstance().loadComments(postId);

                Message message = new Message();
                message.what = NotificationCenter.COMMENT_LOADED;
                message.obj = commentArrayList;
                message.arg1 = 0;
                cloudQueue.sendMessage(message, 0);

                Log.i("forDebug", "fetchComments: load from server1");
            });
        } else {
            storageQueue.postRunnable(() -> {
                ArrayList<Comment> comments = StorageManager.getInstance().loadCommentsFromDB(postId);
                if (comments != null) {
                    commentArrayList = comments;

                    Message message = new Message();
                    message.what = NotificationCenter.COMMENT_LOADED;
                    message.obj = commentArrayList;
                    message.arg1 = 0;
                    storageQueue.sendMessage(message, 0);

                    Log.i("forDebug", "fetchComments: load from DB");
                } else {
                    cloudQueue.postRunnable(() -> {
                        commentArrayList = ConnectionManager.getInstance().loadComments(postId);

                        Message message = new Message();
                        message.what = NotificationCenter.COMMENT_LOADED;
                        message.obj = commentArrayList;
                        message.arg1 = 0;
                        cloudQueue.sendMessage(message, 0);

                        Log.i("forDebug", "fetchComments: load from server2");
                    });
                }
            });
        }
    }
}
