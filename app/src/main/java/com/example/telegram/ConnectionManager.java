package com.example.telegram;

import java.util.ArrayList;

public class ConnectionManager {
    private static final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();
    public static ConnectionManager getInstance(){return CONNECTION_MANAGER;}
    private ConnectionManager(){

    }
    public ArrayList<Integer> load(Integer param){
        /*try {
            this.wait(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        ArrayList<Integer> loadR = new ArrayList<>();
        int i = 1;
        while (i<=10){
            loadR.add(i+param);
            i++;
        }
        return loadR;
    }
}
