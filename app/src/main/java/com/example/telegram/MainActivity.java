package com.example.telegram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotificationCenter.NotificationCenterDelegate {

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;

    private MessageController controller = MessageController.getInstance();
    private ViewStub stubGrid;
    private ViewStub stubList;
    private List<Card> cards = new ArrayList<>();
    private TextView state;
    private int currentViewMode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.POST_LOADED);
        StorageManager.getInstance().setDataBaseHelper(new DataBaseHelper(this));

        super.onCreate(savedInstanceState);
        Log.i("states", "onCreate: ");
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        stubList = findViewById(R.id.stub_list);
        stubList.inflate();

        stubGrid = findViewById(R.id.stub_grid);
        stubGrid.inflate();

        state = findViewById(R.id.state);
        state.setText(R.string.updating);

        currentViewMode = getSharedPreferences("ViewMode", MODE_PRIVATE).getInt("currentViewMode", VIEW_MODE_LISTVIEW);

        switchView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("states", "onStart: ");

        if (ConnectionManager.isNetworkAvailable(getActivity()))
            controller.fetchPost();
        else {
            state.setText(R.string.waiting);

            new Thread(() -> {
                while (!ConnectionManager.isNetworkAvailable(getActivity())) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                controller.fetchPost();
            }).start();
        }
    }

    private void switchView() {
        if (currentViewMode == VIEW_MODE_LISTVIEW) {
            stubList.setVisibility(View.VISIBLE);
            stubGrid.setVisibility(View.GONE);
        } else {
            stubGrid.setVisibility(View.VISIBLE);
            stubList.setVisibility(View.GONE);
        }
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

    private MainActivity getActivity() {
        return this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("states", "onPause: ");
    }

    @Override
    protected void onDestroy() {
        Log.i("states", "onDestroy: ");

        super.onDestroy();
        NotificationCenter.getInstance().removeObserver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("states", "onStop: ");
    }

    @Override
    public void didReceivedNotification(int eventId, Object... args) {
        ArrayList<Post> posts = (ArrayList<Post>) args[0];

        runOnUiThread(() -> {
            cards = new ArrayList<>();
            for (int i = 0; i < posts.size(); i++) {
                Post post = posts.get(i);
                cards.add(new Card(post.getBody(), Card.TYPE_POST, post.getTitle(),post.getID()));
            }

            Button button = findViewById(R.id.refreshBtn);
            button.setOnClickListener(v -> {
                Log.i("asdf", "refresh");
                if (currentViewMode == VIEW_MODE_LISTVIEW)
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                else
                    currentViewMode = VIEW_MODE_LISTVIEW;

                switchView();

                getSharedPreferences("ViewMode", MODE_PRIVATE).edit().putInt("currentViewMode", currentViewMode).apply();
            });

            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(new ImageAdapter(this, R.layout.list_view, cards));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                    Card card = cards.get(i);
                    Log.i("asdf", String.valueOf(card.getPostId()));
                    Main2Activity.getInstance().setPostId(card.getPostId());
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                }
            });

            GridView gridView = findViewById(R.id.gridView);
            gridView.setAdapter(new ImageAdapter(this, R.layout.grid_view, cards));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                    Card card = cards.get(i);
                    Log.i("asdf", String.valueOf(card.getPostId()));
                    Main2Activity.getInstance().setPostId(card.getPostId());
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                }
            });
            state.setText(R.string.connected);
        });
    }
}
