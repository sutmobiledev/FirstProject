package com.example.telegram;

import android.os.Message;

import java.util.ArrayList;

public class MessageController {
    private final int updatePeriod = 5 * 60;
    private long lastPostUpdate = 0, lastCommentsUpdate = 0;

    private static final MessageController messageControllerInstance = new MessageController();
    private DispatchQueue storageQueue, cloudQueue;

    private ArrayList<Post> postArrayList;
    private ArrayList<Comment> commentArrayList;

    private MessageController() {
        cloudQueue = new DispatchQueue("Cloud") {
            @Override
            public void handleMessage(Message inputMessage) {
                if (inputMessage.what == NotificationCenter.POST_LOADED) {
                    storageQueue.postRunnable(() -> StorageManager.getInstance().savePostsToDB(postArrayList));

                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj);
                } else if (inputMessage.what == NotificationCenter.COMMENT_LOADED) {
                    storageQueue.postRunnable(() -> StorageManager.getInstance().saveCommentsToDB(commentArrayList));

                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj);
                }
            }
        };
        storageQueue = new DispatchQueue("Storage") {
            @Override
            public void handleMessage(Message inputMessage) {
                if (inputMessage.what == NotificationCenter.POST_LOADED) {
                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj);
                } else if (inputMessage.what == NotificationCenter.COMMENT_LOADED) {
                    NotificationCenter.getInstance().data_loaded(inputMessage.what, inputMessage.obj);
                }
            }
        };

        postArrayList = new ArrayList<>();
        commentArrayList = new ArrayList<>();
    }

    public static MessageController getInstance() {
        return messageControllerInstance;
    }

    public void fetchPost() {
        if (System.currentTimeMillis() - lastPostUpdate > updatePeriod) {
            lastPostUpdate = System.currentTimeMillis();

            cloudQueue.postRunnable(() -> {
                postArrayList = ConnectionManager.getInstance().loadPosts();

                Message message = new Message();
                message.what = NotificationCenter.POST_LOADED;
                message.obj = postArrayList;
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
                    storageQueue.sendMessage(message, 0);
                } else {
                    cloudQueue.postRunnable(() -> {
                        postArrayList = ConnectionManager.getInstance().loadPosts();

                        Message message = new Message();
                        message.what = NotificationCenter.POST_LOADED;
                        message.obj = postArrayList;
                        cloudQueue.sendMessage(message, 0);
                    });
                }
            });
        }
    }

    public void fetchComments(int postId) {
        if (System.currentTimeMillis() - lastCommentsUpdate > updatePeriod) {
            lastCommentsUpdate = System.currentTimeMillis();

            cloudQueue.postRunnable(() -> {
                commentArrayList = ConnectionManager.getInstance().loadComments(postId);

                Message message = new Message();
                message.what = NotificationCenter.COMMENT_LOADED;
                message.obj = commentArrayList;
                cloudQueue.sendMessage(message, 0);
            });
        } else {
            storageQueue.postRunnable(() -> {
                ArrayList<Comment> comments = StorageManager.getInstance().loadCommentsFromDB(postId);
                if (comments != null) {
                    commentArrayList = comments;

                    Message message = new Message();
                    message.what = NotificationCenter.COMMENT_LOADED;
                    message.obj = commentArrayList;
                    storageQueue.sendMessage(message, 0);
                } else {
                    cloudQueue.postRunnable(() -> {
                        commentArrayList = ConnectionManager.getInstance().loadComments(postId);

                        Message message = new Message();
                        message.what = NotificationCenter.COMMENT_LOADED;
                        message.obj = commentArrayList;
                        cloudQueue.sendMessage(message, 0);
                    });
                }
            });
        }
    }
}
