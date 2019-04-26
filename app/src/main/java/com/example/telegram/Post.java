package com.example.telegram;

public class Post {
    private int ID, userID;
    private String title, body;

    public Post(int ID, int userID, String title, String body) {
        this.ID = ID;
        this.userID = userID;
        this.title = title;
        this.body = body;
    }

    public int getID() {
        return ID;
    }

    public int getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
