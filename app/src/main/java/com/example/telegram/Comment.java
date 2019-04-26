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

    public Comment() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
