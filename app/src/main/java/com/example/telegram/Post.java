package com.example.telegram;

import java.util.ArrayList;

public class Post {
    private int ID, userID;
    private String title, body;
    private ArrayList<Comment> comments;

    public Post(int ID, int userID, String title, String body, ArrayList<Comment> comments) {
        this.ID = ID;
        this.userID = userID;
        this.title = title;
        this.body = body;
        this.comments = comments;
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

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
