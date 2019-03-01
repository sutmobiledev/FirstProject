package com.example.telegram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NotificationCenter.NotificationCenterDelegate {

    Button refreshBtn, getBtn, clearBtn;
    ArrayList<TextView> texts = new ArrayList<>();
    MessageController controller = MessageController.getInstance();
    LinearLayout layout;
    Integer lastNUm;
    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.DATA_LOADED);
        lastNUm = 0;

        super.onCreate(savedInstanceState);
        Log.i("create", "onCreate: ");
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.Layout);

        getBtn = findViewById(R.id.getBtn);
        getBtn.setOnClickListener(v -> controller.fetch(false, lastNUm));

        refreshBtn = findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(v -> controller.fetch(true, null));

        clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(v -> layout.removeAllViews());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("start", "onStart: ");
        try {
            fileOutputStream = openFileOutput("Data.txt", MODE_PRIVATE);
            fileInputStream = openFileInput("Data.txt");

            StorageManager.getInstance().setFileOutputStream(fileOutputStream);
            StorageManager.getInstance().setFileInputStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("start", "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationCenter.getInstance().removeObserver(this);
        try {
            fileOutputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("start", "onStop: ");
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        runOnUiThread(() -> {
            ArrayList<Integer> arrayList = (ArrayList<Integer>) args[0];
            texts.add(new TextView(this));
            layout.addView(texts.get(texts.size() - 1));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i : arrayList) {
                stringBuilder.append(i);
                stringBuilder.append(" ");
            }
            texts.get(texts.size() - 1).setText(stringBuilder);
            lastNUm = arrayList.get(arrayList.size() - 1);
        });
    }
}
