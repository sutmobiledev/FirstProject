package com.example.telegram;

public class Card {
    public static final int TYPE_POST = 0, TYPE_COMMENT = 1;
    private String title, name, body;
    private int type;
    private int postId;

    public Card(String body, int type, String title_name,int postId) {
        this.body = body;
        this.type = type;
        this.postId = postId;
        if (type == TYPE_POST)
            this.title = title_name;
        else
            this.name = title_name;
    }

    public String getName() {
        if (title != null)
            return title;
        else
            return name;
    }

    public String getAuthor() {
        return body;
    }

    public int getPostId() {
        return postId;
    }
}
