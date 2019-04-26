package com.example.telegram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "postCommentDb";
    private static final String POSTS_TABLE = "posts";
    private static final String COMMENTS_TABLE = "comments";

    private static final String POSTS_TABLE_COL0 = "id";
    private static final String POSTS_TABLE_COL1 = "userId";
    private static final String POSTS_TABLE_COL2 = "title";
    private static final String POSTS_TABLE_COL3 = "body";

    private static final String COMMENTS_TABLE_COL0 = "id";
    private static final String COMMENTS_TABLE_COL1 = "postId";
    private static final String COMMENTS_TABLE_COL2 = "name";
    private static final String COMMENTS_TABLE_COL3 = "email";
    private static final String COMMENTS_TABLE_COL4 = "body";

    private static final String CREATE_POSTS_TABLE = "CREATE TABLE " + POSTS_TABLE + "("
            + POSTS_TABLE_COL0 + " INTEGER PRIMARY KEY, " + POSTS_TABLE_COL1
            + " INTEGER, " + POSTS_TABLE_COL2 + " TEXT, " + POSTS_TABLE_COL3 + " TEXT )";

    private static final String CREATE_COMMENTS_TABLE = "CREATE TABLE " + COMMENTS_TABLE
            + "(" + COMMENTS_TABLE_COL0 + " INTEGER PRIMARY KEY, " + COMMENTS_TABLE_COL1
            + " INTEGER, " + COMMENTS_TABLE_COL2 + " TEXT, " + COMMENTS_TABLE_COL3
            + " TEXT, " + COMMENTS_TABLE_COL4 + " TEXT )";


    public DataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_POSTS_TABLE);
        db.execSQL(CREATE_COMMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + POSTS_TABLE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + COMMENTS_TABLE + "'");
        onCreate(db);
    }

    public void addPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues postContentValues = new ContentValues();
        postContentValues.put(POSTS_TABLE_COL0, post.getID());
        postContentValues.put(POSTS_TABLE_COL1, post.getUserID());
        postContentValues.put(POSTS_TABLE_COL2, post.getTitle());
        postContentValues.put(POSTS_TABLE_COL3, post.getBody());
        db.insert(POSTS_TABLE, null, postContentValues);
        db.close();
        for (Comment c : post.getComments()) {
            addComment(c);
        }
    }

    private void addComment(Comment comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues commentContentValues = new ContentValues();
        commentContentValues.put(POSTS_TABLE_COL0, comment.getID());
        commentContentValues.put(POSTS_TABLE_COL1, comment.getPostID());
        commentContentValues.put(POSTS_TABLE_COL2, comment.getName());
        commentContentValues.put(POSTS_TABLE_COL3, comment.getEmail());
        commentContentValues.put(POSTS_TABLE_COL3, comment.getBody());
        db.insert(COMMENTS_TABLE, null, commentContentValues);
        db.close();
    }

    public ArrayList<Post> getPost(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> posts = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + POSTS_TABLE, null);
        Log.i("get post:",Integer.toString(cursor.getColumnCount()));
        if (cursor.moveToFirst()) {
            do {
                Post post = new Post();
                post.setID(cursor.getInt(0));
                post.setUserID(cursor.getInt(1));
                post.setTitle(cursor.getString(2));
                post.setBody(cursor.getString(3));

                posts.add(post);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return null;
    }
}
