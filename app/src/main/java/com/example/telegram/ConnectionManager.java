package com.example.telegram;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ConnectionManager {
    private static final ConnectionManager connectionManagerInstance = new ConnectionManager();

    private ConnectionManager() {

    }

    public static ConnectionManager getInstance() {
        return connectionManagerInstance;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            Network[] networks = connectivity.getAllNetworks();
            if (networks != null) {
                for (Network network : networks) {
                    NetworkInfo info = connectivity.getNetworkInfo(network);
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String readFromURL(String dest_url) {
        StringBuilder res = new StringBuilder();

        try {
            URL url = new URL(dest_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.connect();
            Scanner scanner = new Scanner(httpURLConnection.getInputStream());

            String line;
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                res.append(line).append('\n');
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res.toString();
    }

    public ArrayList<Post> loadPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        JSONArray jsonPostArray;

        String posts_str = readFromURL("https://jsonplaceholder.typicode.com/posts");
        try {
            jsonPostArray = new JSONArray(posts_str);

            int j = 0;
            for (int i = 0; i < jsonPostArray.length(); i++) {
                JSONObject post = jsonPostArray.getJSONObject(i);

                posts.add(
                        new Post(
                                post.getInt("id"),
                                post.getInt("userId"),
                                post.getString("title"),
                                post.getString("body")
                        )
                );

                System.out.print(posts.get(i).getID());
                System.out.print(":\t");
//                ArrayList<Comment> comments = loadComments(posts.get(i).getID());
//                for (int k = 0; k < comments.size(); k++) {
//                    System.out.print(comments.get(k).getPostID());
//                    System.out.print('\t');
//                }
                System.out.println("\n----------------------");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("LogInfo:ConnectionManager: ", "Load Done!");

        return posts;
    }

    public ArrayList<Comment> loadComments(int postId) {
        ArrayList<Comment> comments = new ArrayList<>();
        JSONArray jsonCommentsArray;

        String comments_str = readFromURL("https://jsonplaceholder.typicode.com/comments?postId=" + String.valueOf(postId));

        try {
            jsonCommentsArray = new JSONArray(comments_str);
            for (int i = 0; i < jsonCommentsArray.length(); i++) {
                JSONObject comment = jsonCommentsArray.getJSONObject(i);

                comments.add(
                        new Comment(
                                comment.getInt("id"),
                                comment.getInt("postId"),
                                comment.getString("name"),
                                comment.getString("email"),
                                comment.getString("body")
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return comments;
    }
}
