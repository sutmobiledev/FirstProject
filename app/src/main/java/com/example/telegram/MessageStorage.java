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
    private static final MessageStorage messageStorage = new MessageStorage();

    private File file = new File("/Users/sana/Desktop/file.txt");
    private FileInputStream fileInputStream;

    public static MessageStorage getInstance(){return messageStorage;}
    private MessageStorage(){

    }

    {
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
    private BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    private FileOutputStream fileOutputStream;

    {
        try {
            fileOutputStream = new FileOutputStream(file,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        ArrayList<Integer> loadR = new ArrayList<>();
        for (int i = 1 ; i <= 10 ; i++)
            loadR.add(i+n);

        return loadR;
    }
}
