package com.example.telegram;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MessageStorage {
    private File file = new File("/Users/sana/Desktop/file.txt");
    FileInputStream fileInputStream;

    {
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    FileOutputStream fileOutputStream;

    {
        try {
            fileOutputStream = new FileOutputStream(file,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    ArrayList<Integer> loadR;
    private static final MessageStorage messageStorage = new MessageStorage();
    public static MessageStorage getInstance(){return messageStorage;}
    private MessageStorage(){

    }

    private DispatchQueue storageQueue = new DispatchQueue("storageQueue");

    public void f1(){
        Log.i("", "MessageStorage::f1");
        storageQueue.postRunnable(() -> Log.i("", "MessageStorage::f1:postRunnable"));
    }
    public void save(Integer lastNum){
        try {
            fileOutputStream.write(lastNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Integer> load(){
        int n = 0;
        try {
            n =bufferedReader.read();
        } catch (IOException e) {
            e.printStackTrace();

        }
        loadR = new ArrayList<>();
        int i = 1;
        while (i<=10){
            loadR.add(i+n);

        }
        return loadR;
    }
}
