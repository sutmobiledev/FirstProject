package com.example.telegram;

import java.util.ArrayList;

public class ConnectionManager {
    private static final ConnectionManager connectionManagerInstance = new ConnectionManager();

    private ConnectionManager() {

    }

    public static ConnectionManager getInstance() {
        return connectionManagerInstance;
    }

    public ArrayList<Integer> load(Integer param) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> loadR = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            loadR.add(i + param);

        return loadR;
    }
}
