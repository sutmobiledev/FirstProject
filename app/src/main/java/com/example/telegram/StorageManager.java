package com.example.telegram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StorageManager {
    private File file = new File("Data");
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
    //private static final StorageManager STORAGE_MANAGER = new StorageManager();
    //public static StorageManager getInstance(){return STORAGE_MANAGER;}
    public StorageManager(){

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
        save(i+n-1);
        return loadR;
    }
}
