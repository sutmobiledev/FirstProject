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

public class StorageManager {
    private static final StorageManager ourInstance = new StorageManager();

    private FileInputStream fileInputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private FileOutputStream fileOutputStream;

    ArrayList<Integer> loadR;

    public static StorageManager getInstance(){return ourInstance;}
    private StorageManager(){
        File file = new File("com/example/telegram/Data.txt");
        {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        inputStreamReader = new InputStreamReader(fileInputStream);
        bufferedReader = new BufferedReader(inputStreamReader);

        {
            try {
                fileOutputStream = new FileOutputStream(file,false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
        loadR = new ArrayList<>();
        int i = 1;
        while (i<=10){
            loadR.add(i+n);
            i++;
        }
        //save(i+n-1);
        return loadR;
    }
}
