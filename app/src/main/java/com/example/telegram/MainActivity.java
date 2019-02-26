package com.example.telegram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("creat", "onCreate: ");
        setContentView(R.layout.activity_main);

        AutoCompleteTextView textView = findViewById(R.id.autoComView);
        String[] countries = getResources().getStringArray(R.array.countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, countries);
        textView.setAdapter(adapter);

        Button okButton = findViewById(R.id.okBtn);
        okButton.setOnClickListener((View v) ->{
                    Controller.getInstance().doJob();
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
}
