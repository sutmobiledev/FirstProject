package com.example.telegram;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class StorageManager {
    private static final StorageManager ourInstance = new StorageManager();

    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;

    private StorageManager() {

    }

    public static StorageManager getInstance() {
        return ourInstance;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }

    public synchronized void save_file(Integer lastNum) {
        try {
            fileOutputStream.write(lastNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized int load_file() {
        StringBuilder s;
        s = new StringBuilder();
        int readed =0;
        int nowRead = 0;
        try {
            if (fileInputStream.getChannel().size() != 0) {
                readed = fileInputStream.read();
                nowRead = readed;
                do {
                    readed = nowRead;
                    nowRead = fileInputStream.read();
                }while (nowRead>readed);
                return readed;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<Integer> load() {
        int n = load_file();

        ArrayList<Integer> loadR = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            loadR.add(i + n);

        save_file(n + 10);
        return loadR;
    }
}
