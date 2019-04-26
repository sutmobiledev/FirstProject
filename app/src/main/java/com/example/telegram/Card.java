package com.example.telegram;

public class Card {
    public static final int TYPE_POST = 0, TYPE_COMMENT = 1;
    private String title, name, body;
    private int type;

    public Card(String body, int type, String title_name) {
        this.body = body;
        this.type = type;

        if (type == TYPE_POST)
            this.title = title_name;
        else
            this.name = title_name;
    }

    public String getName() {
        return title;
    }

    public String getAuthor() {
        return body;
    }
}
