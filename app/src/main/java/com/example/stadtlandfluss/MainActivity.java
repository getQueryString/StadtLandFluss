// Copyright© by Fin

package com.example.stadtlandfluss;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
    public boolean gameover = false;
    public boolean countDownStarted = false;

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

        stadt.setEnabled(false);
        land.setEnabled(false);
        fluss.setEnabled(false);

        minus_fivepoints.setVisibility(View.INVISIBLE);
        minus_tenpoints.setVisibility(View.INVISIBLE);

        rdmbtn.setOnClickListener(v -> {
            clearOrNextRound();

            String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
            rdmout.setText("Buchstabe: " + chars[(int) (Math.random() * 10)].toUpperCase());

            rdmbtn.setText("Nächste Runde");


        });

        start.setOnClickListener(v -> {
            startStop();

        });

        clear.setOnClickListener(v -> {
            rdmout.setText("Buchstabe:");
            rdmbtn.setText("Neue Runde");

            pointsCount = 0;
            points.setText("Punkte: 0");

            if (minus_fivepoints.getVisibility() == View.VISIBLE)
                minus_fivepoints.setVisibility(View.INVISIBLE);
            if (minus_tenpoints.getVisibility() == View.VISIBLE)
                minus_tenpoints.setVisibility(View.INVISIBLE);

            clearOrNextRound();
        });

        finish.setOnClickListener(v -> {
            if (countDownStarted == true && gameover == false) {
                finishTimer();

            } else {
                Toast.makeText(getApplicationContext(), "Spiel läuft nicht!", Toast.LENGTH_SHORT).show();
            }
        });

        fivepoints.setOnClickListener(v -> {
            if (!timerRunning) {
                pointsCount += 5;
                points.setText("Punkte: " + pointsCount);
            }
            checkPoints();
        });

        tenpoints.setOnClickListener(v -> {
            if (!timerRunning) {
                pointsCount += 10;
                points.setText("Punkte: " + pointsCount);
            }
            checkPoints();
        });

        minus_fivepoints.setOnClickListener(v -> {
            if (!timerRunning) {
                if (pointsCount > 0) {
                    pointsCount -= 5;
                    points.setText("Punkte: " + pointsCount);
                }
            }
            checkPoints();
        });

        minus_tenpoints.setOnClickListener(v -> {
            if (!timerRunning) {
                if (pointsCount >= 10) {
                    pointsCount -= 10;
                } else if (pointsCount == 5) {
                    pointsCount -= 5;
                }
                points.setText("Punkte: " + pointsCount);
            }
            checkPoints();
        });

        github.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://github.com/getQueryString/StadtLandFluss"));
            startActivity(intent);
        });
    }

    public void checkPoints() {
        if (pointsCount >= 5) minus_fivepoints.setVisibility(View.VISIBLE);
        if (pointsCount >= 10) {
            minus_fivepoints.setVisibility(View.VISIBLE);
            minus_tenpoints.setVisibility(View.VISIBLE);
        }
        if (pointsCount < 5) {
            minus_fivepoints.setVisibility(View.INVISIBLE);
            if (minus_tenpoints.getVisibility() == View.VISIBLE) {
                minus_tenpoints.setVisibility(View.INVISIBLE);
            }
        }
        if (pointsCount < 10) minus_tenpoints.setVisibility(View.INVISIBLE);
        else if (pointsCount == 0) {
            minus_fivepoints.setVisibility(View.INVISIBLE);
            minus_tenpoints.setVisibility(View.INVISIBLE);
        }
    }

    public void clearOrNextRound() {
        stopTimer();
        finished = false;
        gameover = false;
        countDownStarted = false;
        stadt.setEnabled(false);
        land.setEnabled(false);
        fluss.setEnabled(false);
        stadt.setText("");
        land.setText("");
        fluss.setText("");
        if (stadt.getText().toString().isEmpty()) stadt.setHintTextColor(Color.WHITE);
        if (land.getText().toString().isEmpty()) land.setHintTextColor(Color.WHITE);
        if (fluss.getText().toString().isEmpty()) fluss.setHintTextColor(Color.WHITE);
        finish.setBackgroundColor(Color.parseColor("#01DF01"));

    }

    public void resetPointButtons() {
        System.out.println("IUDHFNWURDE RESETTETETTT");
        if (fivepoints.getVisibility() == View.INVISIBLE) {
            fivepoints.setVisibility(View.VISIBLE);
        }
        if (tenpoints.getVisibility() == View.INVISIBLE) {
            tenpoints.setVisibility(View.VISIBLE);
        }
        if (minus_fivepoints.getVisibility() == View.INVISIBLE && pointsCount >= 5) {
            minus_fivepoints.setVisibility(View.VISIBLE);
        }
        if (minus_tenpoints.getVisibility() == View.INVISIBLE && pointsCount >= 10) {
            minus_tenpoints.setVisibility(View.VISIBLE);
        }
    }

    public void startStop() {
        if (timerRunning) {
            pauseTimer();
        } else if (finished == false) {
            startTimer();
        } else if (gameover == true) {
            Toast.makeText(getApplicationContext(), "Spiel läuft nicht!", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTimer() {
        finish.setBackgroundColor(Color.parseColor("#01DF01"));
        fivepoints.setVisibility(View.INVISIBLE);
        tenpoints.setVisibility(View.INVISIBLE);
        minus_fivepoints.setVisibility(View.INVISIBLE);
        minus_tenpoints.setVisibility(View.INVISIBLE);
        if (!timerRunning) {
            countDownStarted = true;
            countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMilliseconds = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    finishTimer();
                }
            }.start();
            start.setText("Pause");
            start.setBackgroundColor(Color.GREEN);
            timerRunning = true;
            if (timerRunning) {
                stadt.setEnabled(true);
                land.setEnabled(true);
                fluss.setEnabled(true);
            }
        }
    }

    public void pauseTimer() {
        resetPointButtons();

        countDownTimer.cancel();
        start.setText("Resume");
        start.setBackgroundColor(Color.RED);
        timerRunning = false;
        stadt.setEnabled(false);
        land.setEnabled(false);
        fluss.setEnabled(false);
    }

    public void finishTimer() {
        resetPointButtons();

        if (timerRunning) countDownTimer.cancel();
        start.setText("Beendet");
        start.setBackgroundColor(Color.RED);
        finish.setBackgroundColor(Color.RED);

        timerRunning = false;
        finished = true;
        gameover = true;
        countDownStarted = false;

        stadt.setEnabled(false);
        land.setEnabled(false);
        fluss.setEnabled(false);

        if (stadt.getText().toString().isEmpty()) stadt.setHintTextColor(Color.RED);
        if (land.getText().toString().isEmpty()) land.setHintTextColor(Color.RED);
        if (fluss.getText().toString().isEmpty()) fluss.setHintTextColor(Color.RED);
    }

    public void stopTimer() {
        resetPointButtons();

        if (timerRunning) countDownTimer.cancel();
        start.setText("Start");
        start.setBackgroundColor(Color.RED);
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