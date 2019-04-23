package com.example.telegram;

public class Comment {
    private int ID, postID;
    private String name, email, body;

    public Comment(int ID, int postID, String name, String email, String body) {
        this.ID = ID;
        this.postID = postID;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getID() {
        return ID;
    }

    public int getPostID() {
        return postID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
