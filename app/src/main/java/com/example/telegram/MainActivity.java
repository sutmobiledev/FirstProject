package com.example.telegram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NotificationCenter.NotificationCenterDelegate {

    Button refreshBtn, getBtn, clearBtn;
    TextView textView;
    MessageController controller = MessageController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.DATA_LOADED);

        super.onCreate(savedInstanceState);
        Log.i("create", "onCreate: ");
        setContentView(R.layout.activity_main);

        getBtn = findViewById(R.id.getBtn);
        clearBtn = findViewById(R.id.clearBtn);
        refreshBtn = findViewById(R.id.refreshBtn);
        getBtn.setOnClickListener(v -> controller.fetch(false));
        refreshBtn.setOnClickListener(v -> controller.fetch(true));
        clearBtn.setOnClickListener(v -> {
            LinearLayout linearLayout = findViewById(R.id.Layout);
            linearLayout.removeAllViews();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("start", "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("start", "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("start", "onDestroy: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("start", "onStop: ");
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {

    }
}
