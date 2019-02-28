package com.example.telegram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NotificationCenter.NotificationCenterDelegate {

    Button refreshBtn, getBtn, clearBtn;
    ArrayList<TextView> texts = new ArrayList<>();
    MessageController controller = MessageController.getInstance();
    LinearLayout layout = findViewById(R.id.Layout);
    Integer lastNUm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.DATA_LOADED);

        super.onCreate(savedInstanceState);
        Log.i("create", "onCreate: ");
        setContentView(R.layout.activity_main);

        getBtn = findViewById(R.id.getBtn);
        clearBtn = findViewById(R.id.clearBtn);
        refreshBtn = findViewById(R.id.refreshBtn);
        getBtn.setOnClickListener(v -> controller.fetch(false,lastNUm));
        refreshBtn.setOnClickListener(v -> controller.fetch(true,null));
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
        ArrayList<Integer> arrayList = (ArrayList<Integer>) args[0];
        texts.add(new TextView(this));
        layout.addView(texts.get(texts.size()-1));
        StringBuilder stringBuilder = new StringBuilder();
        for(int i:arrayList){
            stringBuilder.append(i);
        }
        lastNUm = arrayList.get(arrayList.size()-1);
        texts.get(texts.size()-1).setText(stringBuilder);
    }
}
