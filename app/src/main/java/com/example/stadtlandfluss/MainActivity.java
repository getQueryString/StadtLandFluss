package com.example.stadtlandfluss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button rdmbtn;
    Button clear;
    TextView rdmout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rdmbtn = findViewById(R.id.rdm_btn);
        clear = findViewById(R.id.clear_btn);
        rdmout = findViewById(R.id.rdmletterout);

        rdmbtn.setOnClickListener(v -> {

            String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
            rdmout.setText("Buchstabe: " + chars[(int) (Math.random() * 10)].toUpperCase());

            rdmbtn.setText("Neuer Zufallsbuchstabe");
        });

        clear.setOnClickListener(v -> {
            rdmout.setText("Buchstabe:");
            rdmbtn.setText("Zufallsbuchstabe");
            Toast.makeText(getApplicationContext(), "Cleared", Toast.LENGTH_SHORT).show();
        });
    }
}