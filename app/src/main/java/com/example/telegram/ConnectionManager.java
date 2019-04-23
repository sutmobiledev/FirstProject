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
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public ArrayList<Post> load(Integer param) {
        ArrayList<Post> loadR = new ArrayList<>();
        JSONArray jsonPostArray, jsonCommentsArray;

        String posts = readFromURL("https://jsonplaceholder.typicode.com/posts");
        String comments = readFromURL("https://jsonplaceholder.typicode.com/posts/1/comments");
        try {
            jsonPostArray = new JSONArray(posts);
            jsonCommentsArray = new JSONArray(comments);

            int j = 0;
            for (int i = 0; i < jsonPostArray.length(); i++) {
                JSONObject post = jsonPostArray.getJSONObject(i);
                ArrayList<Comment> commentsArrayList = new ArrayList<>();

                while (j < jsonCommentsArray.length() && jsonCommentsArray.getJSONObject(j).getInt("postId") == post.getInt("id")) {
                    JSONObject comment = jsonCommentsArray.getJSONObject(j);

                    commentsArrayList.add(new Comment(
                            comment.getInt("id"),
                            comment.getInt("postId"),
                            comment.getString("name"),
                            comment.getString("email"),
                            comment.getString("body")
                    ));

                    j++;
                }

                loadR.add(
                        new Post(
                                post.getInt("id"),
                                post.getInt("userId"),
                                post.getString("title"),
                                post.getString("body"),
                                commentsArrayList
                        )
                );

//                System.out.print(loadR.get(i).getID());
//                System.out.print(":\t");
//                for (int k = 0; k < loadR.get(i).getComments().size(); k++){
//                    System.out.print(loadR.get(i).getComments().get(k).getID());
//                    System.out.print('\t');
//                }
//                System.out.println("\n----------------------");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("LogInfo:ConnectionManager: ", "Load Done!");

        return loadR;
    }
}
