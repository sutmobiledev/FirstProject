package com.example.telegram;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class StorageManager {
    private static final StorageManager storageManagerInstance = new StorageManager();

    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private Formatter formatter;
    private Scanner scanner;
    private int last_num_in_file = 0;
    private DataBaseHelper dataBaseHelper;

    private StorageManager() {
    }

    public static StorageManager getInstance() {
        return storageManagerInstance;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
        scanner = new Scanner(fileInputStream);
    }

    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
        formatter = new Formatter(fileOutputStream);
    }

    public void save_file(Integer lastNum) {
        formatter = new Formatter(fileOutputStream);
        formatter.format("%d\n", lastNum);
        formatter.flush();
    }

    private int load_file() {
        scanner = new Scanner(fileInputStream);
        try {
            if (fileInputStream.getChannel().size() == 0)
                return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        int temp;
        while (scanner.hasNextInt() && (temp = scanner.nextInt()) > -1) {
            last_num_in_file = temp;
            System.out.println("last_num_in_file = " + last_num_in_file);
        }

        return last_num_in_file;
    }

    public ArrayList<Integer> load(int param) {
        ArrayList<Integer> loadR = new ArrayList<>();
        int n = load_file();

        if (param >= n)
            return loadR;

        for (int i = 1; i <= 10; i++)
            loadR.add(i + param);

        return loadR;
    }

    public void saveToDB(ArrayList<Post> data) {
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

    public ArrayList<Post> loadFromDB() {
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
