package com.example.stadtlandfluss;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * TODO
     * +5 & +10 Punkte
     */

    // Button
    Button rdmbtn;
    Button clear;
    Button start;
    Button finish;
    Button fivepoints;
    Button tenpoints;
    Button minus_fivepoints;
    Button minus_tenpoints;

    // TextView
    TextView rdmout;
    TextView countdown;
    TextView points;

    // EditText
    EditText stadt;
    EditText land;
    EditText fluss;

    // ImageView
    ImageView github;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000;

    public boolean timerRunning;
    public boolean finished = false;
    public int pointsCount = 0;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(Configuration.ORIENTATION_PORTRAIT);

        // Button
        rdmbtn = findViewById(R.id.rdm_btn);
        clear = findViewById(R.id.clear_btn);
        start = findViewById(R.id.start_btn);
        finish = findViewById(R.id.finish_btn);
        fivepoints = findViewById(R.id.five_points_btn);
        tenpoints = findViewById(R.id.ten_points_btn);
        minus_fivepoints = findViewById(R.id.minus_five_points_btn);
        minus_tenpoints = findViewById(R.id.minus_ten_points_btn);

        // TextView
        rdmout = findViewById(R.id.rdmletterout_txt);
        countdown = findViewById(R.id.countdown_txt);
        points = findViewById(R.id.points_txt);

        // EditText
        stadt = findViewById(R.id.txt_stadt);
        land = findViewById(R.id.txt_land);
        fluss = findViewById(R.id.txt_fluss);

        // ImageView
        github = findViewById(R.id.img_github);

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

            stopTimer();
        });

        start.setOnClickListener(v -> {
            startStop();

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

            pointsCount = 0;
            points.setText("Punkte: 0");

            stopTimer();
            finished = false;
        });

        finish.setOnClickListener(v -> {
            stadt.setEnabled(false);
            land.setEnabled(false);
            fluss.setEnabled(false);

            finishTimer();
        });

        fivepoints.setOnClickListener(v -> {
            pointsCount += 5;
            points.setText("Punkte: " + pointsCount);
        });

        tenpoints.setOnClickListener(v -> {
            pointsCount += 10;
            points.setText("Punkte: " + pointsCount);
        });

        minus_fivepoints.setOnClickListener(v -> {
            if (pointsCount > 0) {
                pointsCount -= 5;
                points.setText("Punkte: " + pointsCount);
            }
        });

        minus_tenpoints.setOnClickListener(v -> {
            if (pointsCount > 0 && !(pointsCount == 5)) {
                pointsCount -= 10;
                points.setText("Punkte: " + pointsCount);
            }
        });

        github.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://github.com/getQueryString/StadtLandFluss"));
            startActivity(intent);
        });
        updateTimer();
    }

    public void startStop() {
        if (timerRunning) {
            pauseTimer();
        } else {
            if (finished == false) {
                startTimer();
            }
        }
    }

    public void startTimer() {
        if (!timerRunning) {
            countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMilliseconds = millisUntilFinished;
                    updateTimer();

                }

                @Override
                public void onFinish() {

                }
            }.start();
            start.setText("Pause");
            start.setBackgroundColor(Color.rgb(0, 255, 0));
            timerRunning = true;
        }
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        start.setText("Resume");
        start.setBackgroundColor(Color.rgb(255, 0, 0));
        timerRunning = false;
    }

    public void finishTimer() {
        if (timerRunning) countDownTimer.cancel();
        start.setText("Beendet");
        start.setBackgroundColor(Color.rgb(255, 0, 0));
        timerRunning = false;
        finished = true;
    }

    public void stopTimer() {
        if (timerRunning) countDownTimer.cancel();
        start.setText("Start");
        start.setBackgroundColor(Color.rgb(255, 0, 0));
        timerRunning = false;
        timeLeftInMilliseconds = 60000;
        countdown.setText("Zeit: 1:00");
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "Zeit: " + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countdown.setText(timeLeftText);
    }
}