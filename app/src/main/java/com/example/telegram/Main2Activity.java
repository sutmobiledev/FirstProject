package com.example.telegram;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        setSupportActionBar(findViewById(R.id.toolbar));

        ViewStub stubList = findViewById(R.id.stub_list);
        stubList.inflate();

        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            cards.add(new Card());
        }

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new ImageAdapter(this, R.layout.list_view, cards));
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
}

