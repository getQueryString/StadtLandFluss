package com.example.stadtlandfluss;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Button
    Button rdmbtn;
    Button clear;
    Button finish;

    // TextView
    TextView rdmout;

    // EditText
    EditText stadt;
    EditText land;
    EditText fluss;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(Configuration.ORIENTATION_PORTRAIT);

        // Button
        rdmbtn = findViewById(R.id.rdm_btn);
        clear = findViewById(R.id.clear_btn);
        finish = findViewById(R.id.finish_btn);

        // TextView
        rdmout = findViewById(R.id.rdmletterout);

        // EditText
        stadt = findViewById(R.id.txt_stadt);
        land = findViewById(R.id.txt_land);
        fluss = findViewById(R.id.txt_fluss);

        rdmbtn.setOnClickListener(v -> {
            stadt.setText("");
            land.setText("");
            fluss.setText("");

            if (!stadt.isEnabled() && !land.isEnabled() && !fluss.isEnabled()) {
                stadt.setEnabled(true);
                land.setEnabled(true);
                fluss.setEnabled(true);
            }

            String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
            rdmout.setText("Buchstabe: " + chars[(int) (Math.random() * 10)].toUpperCase());

            rdmbtn.setText("Neuer Zufallsbuchstabe");
        });

        clear.setOnClickListener(v -> {
            if (!stadt.isEnabled() && !land.isEnabled() && !fluss.isEnabled()) {
                stadt.setEnabled(true);
                land.setEnabled(true);
                fluss.setEnabled(true);
            }

            rdmout.setText("Buchstabe:");
            rdmbtn.setText("Zufallsbuchstabe");
            stadt.setText("");
            land.setText("");
            fluss.setText("");
        });

        finish.setOnClickListener(v -> {
            stadt.setEnabled(false);
            land.setEnabled(false);
            fluss.setEnabled(false);
        });
    }
}