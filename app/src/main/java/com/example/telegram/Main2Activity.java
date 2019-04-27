package com.example.telegram;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements NotificationCenter.NotificationCenterDelegate {
    private MessageController controller = MessageController.getInstance();
    private List<Card> cards = new ArrayList<>();
    private TextView state;
    private ViewStub stubList;
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.COMMENT_LOADED);

        setContentView(R.layout.activity_main2);
        setSupportActionBar(findViewById(R.id.toolbar));

        stubList = findViewById(R.id.stub_list);
        stubList.inflate();

        state = findViewById(R.id.state);
        state.setText(R.string.updating);
    }

    @Override
    protected void onStart() {
        super.onStart();
        postId = getSharedPreferences("postId", MODE_PRIVATE).getInt("postId", 0);

        if (ConnectionManager.isNetworkAvailable(getActivity()))
            controller.fetchComments(postId, false);
        else {
            state.setText(R.string.waiting);
            controller.fetchComments(postId, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        NotificationCenter.getInstance().removeObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_settings:
                Log.i("asdf", "action_settings");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.names).setTitle(R.string.title);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return true;
        }
    }

    private Main2Activity getActivity() {
        return this;
    }

    @Override
    public void didReceivedNotification(int eventId, Object... args) {
        ArrayList<Comment> comments = (ArrayList<Comment>) args[0];

        runOnUiThread(() -> {
            cards = new ArrayList<>();
            for (int i = 0; i < comments.size(); i++) {
                Comment comment = comments.get(i);
                cards.add(new Card(comment.getBody(), Card.TYPE_COMMENT, comment.getName(),0));
            }

            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(new ImageAdapter(this, R.layout.list_view, cards));

            if ((int) args[1] == 1)
                new Thread(() -> {
                    try {
                        while (!ConnectionManager.isNetworkAvailable(getActivity()))
                            Thread.sleep(500);

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postId = getSharedPreferences("postId", MODE_PRIVATE).getInt("postId", 0);

                    runOnUiThread(() -> state.setText(R.string.updating));
                    controller.fetchComments(postId, false);
                }).start();
            else
                state.setText("Post" +postId + ","+comments.size() + "comments");
        });
    }
}

