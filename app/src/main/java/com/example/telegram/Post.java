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

    public Post() {
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
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
