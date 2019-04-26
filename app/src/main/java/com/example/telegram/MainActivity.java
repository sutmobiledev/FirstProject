package com.example.telegram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotificationCenter.NotificationCenterDelegate {

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    ListView listView;
    ImageAdapter listAdapter;
    ImageAdapter gridAdapter;
    List<Card> cards;
    Button button;
    //    Button refreshBtn, getBtn, clearBtn;
    ArrayList<TextView> texts = new ArrayList<>();
    MessageController controller = MessageController.getInstance();
    //    LinearLayout layout;
    Integer lastNUm = 0;
    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;
    private ViewStub stubGrid;
    private ViewStub stubList;
    private GridView gridView;
    private int currentViewMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.DATA_LOADED);
        lastNUm = 0;
        StorageManager.getInstance().setDataBaseHelper(new DataBaseHelper(this));

        super.onCreate(savedInstanceState);
        Log.i("states", "onCreate: ");
        setContentView(R.layout.activity_main);

//        layout = findViewById(R.id.Layout);
//
//        getBtn = findViewById(R.id.getBtn);
//        getBtn.setOnClickListener(v -> controller.fetch(false, lastNUm));
//
//        refreshBtn = findViewById(R.id.refreshBtn);
//        refreshBtn.setOnClickListener(v -> controller.fetch(true, lastNUm));
//
//        clearBtn = findViewById(R.id.clearBtn);
//        clearBtn.setOnClickListener(v -> {
//            lastNUm = 0;
//            MessageController.getInstance().getData().clear();
//            layout.removeAllViews();
//        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = findViewById(R.id.refreshBtn);
        stubList = findViewById(R.id.stub_list);
        stubGrid = findViewById(R.id.stub_grid);
        stubList.inflate();
        stubGrid.inflate();
        listView = findViewById(R.id.listView);
        gridView = findViewById(R.id.gridView);
        cards = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            cards.add(new Card());
        }
        switchView();

        listView.setOnItemClickListener((parent, view, position, id) ->
                startActivity(new Intent(MainActivity.this, Main2Activity.class))
        );
        gridView.setOnItemClickListener((parent, view, position, id) ->
                startActivity(new Intent(MainActivity.this, Main2Activity.class))
        );

        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);
        button.setOnClickListener(v -> {
            Log.i("asdf", "refresh");
            if (VIEW_MODE_LISTVIEW == currentViewMode) {
                currentViewMode = VIEW_MODE_GRIDVIEW;
            } else {
                currentViewMode = VIEW_MODE_LISTVIEW;
            }
            switchView();
            SharedPreferences sharedPreferences1 = getSharedPreferences("ViewMode", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("currentViewMode", currentViewMode);
            editor.commit();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("states", "onStart: ");

        didReceivedNotification(0, controller.getData());
    }

    private void switchView() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            stubList.setVisibility(View.VISIBLE);
            stubGrid.setVisibility(View.GONE);
        } else {
            stubGrid.setVisibility(View.VISIBLE);
            stubList.setVisibility(View.GONE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            listAdapter = new ImageAdapter(this, R.layout.list_view, cards);
            listView.setAdapter(listAdapter);
        } else {
            gridAdapter = new ImageAdapter(this, R.layout.grid_view, cards);
            gridView.setAdapter(gridAdapter);
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
        try {
            fileOutputStream.close();
            fileInputStream.close();
            new File("Data.txt").delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("states", "onStop: ");
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        /*runOnUiThread(() -> {
            ArrayList<Integer> arrayList = (ArrayList<Integer>) args[0];

            if (arrayList.isEmpty())
                return;
            if (texts.size() > 0) {
                texts.clear();
            }
            layout.removeAllViews();
            texts.add(new TextView(this));
            layout.addView(texts.get(texts.size() - 1));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i : arrayList) {
                stringBuilder.append(i);
                stringBuilder.append(" ");
            }
            texts.get(texts.size() - 1).setText(stringBuilder);
            lastNUm = arrayList.get(arrayList.size() - 1);
        });*/
    }
}
