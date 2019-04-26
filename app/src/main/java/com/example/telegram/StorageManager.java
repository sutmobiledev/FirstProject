package com.example.telegram;

import android.util.Log;

import java.util.ArrayList;

public class StorageManager {
    private static final StorageManager storageManagerInstance = new StorageManager();

    private DataBaseHelper dataBaseHelper;

    private StorageManager() {
    }

    public static StorageManager getInstance() {
        return storageManagerInstance;
    }

    public void savePostsToDB(ArrayList<Post> data) {
        for (Post p : data) {
            dataBaseHelper.addPost(p);
        }
        Log.i("LogInfo:StorageManager: ", "Save Done!");
    }

    public void SaveCommentsToDB(ArrayList<Comment> data) {
        for (Comment c : data) {
            dataBaseHelper.addComment(c);
        }
        Log.i("LogInfo:StorageManager: ", "Save Comment Done!");
    }

    public ArrayList<Post> loadPostsFromDB() {
        Log.i("LogInfo:StorageManager: ", "Load Done!");
        return  dataBaseHelper.getPost();
    }


    public ArrayList<Comment> loadCommentsFromDB(int postId) {
        Log.i("LogInfo:StorageManager: ", "Posts Load Done!");
        return dataBaseHelper.getComment(postId);
    }

    public void setDataBaseHelper(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }
}
