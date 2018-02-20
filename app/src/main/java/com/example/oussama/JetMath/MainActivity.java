package com.example.oussama.JetMath;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    TextView scoreText;
    ProgressBar progressBar;
    Button correctButton;
    Button wrongButton;
    MediaPlayer mp;
    CountDownTimer countDownTimer;
    float result;
    int score;
    int bonus;
    boolean correctAnswer;
    boolean next;
    boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = (TextView) findViewById(R.id.resultText);
        scoreText = (TextView) findViewById(R.id.score);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setChecked(true);
        sound = checkBox.isChecked();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        correctButton = (Button) findViewById(R.id.correctButton);
        wrongButton = (Button) findViewById(R.id.wrongButton);

        next = false;
        score = 0;
        bonus = -11;

        new CountDownTimer(60000, 10) {
            @Override
            public void onTick(long l) {
                progressBar.setProgress((int) l / 10);
            }

            @Override
            public void onFinish() {
                endOfGame();
            }
        }.start();
        countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                int min = (int) l / 1000 / 60;
                int sec = (int) l / 1000 % 60;
                //clockText.setText(String.format("%02d:%02d", min, sec));
            }

            @Override
            public void onFinish() {
                generateQuestion();
                this.start();
            }
        }.start();
        generateQuestion();
    }


    public void generateQuestion() {
        String color = makeRandomColor();
        //myLayout.setBackgroundColor(Color.parseColor(color));
        int firstNum = new Random().nextInt(100);
        int secondNum = new Random().nextInt(100);
        int operation = new Random().nextInt(4);
        correctAnswer = new Random().nextBoolean();

        String s = "Start !";
        switch (operation) {
            case 0:
                result = (firstNum + secondNum);
                s = firstNum + " + " + secondNum + " =";
                break;
            case 1:
                result = firstNum - secondNum;
                s = firstNum + " - " + secondNum + " =";
                break;
            case 2:
                result = firstNum * secondNum;
                s = firstNum + " x " + secondNum + " =";
                break;
            case 3:
                result = (float) firstNum / secondNum;
                s = firstNum + " / " + secondNum + " =";
                break;
        }
        if (!correctAnswer) {
            int difference = new Random().nextInt(10);
            boolean negative = new Random().nextBoolean();
            if (negative) result -= difference;
            else result += difference;
        }
        if (operation == 3) resultText.setText(s + " " + String.format("%.1f", result));
        else resultText.setText(s + " " + (int) result);
    }

    public void checkIfTrue(View v) {

        if (correctAnswer) {
            mp = MediaPlayer.create(this, R.raw.right);
            bonus += 11;
            score += 100 + bonus;
            scoreText.setText("Score: " + score);
            resultText.setText("Correct!");
        } else {
            mp = MediaPlayer.create(this, R.raw.wrong);
            bonus = -11;
            resultText.setText("Wrong...");
        }
        disablingButtons();
        if (sound) {
            mp.setVolume(1, 1);
        } else
            mp.setVolume(0, 0);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                countDownTimer.onFinish();
                enablingButtons();
            }
        });
    }

    public void checkIfFalse(View v) {
        if (!correctAnswer) {
            mp = MediaPlayer.create(this, R.raw.right);
            bonus += 11;
            score += 100 + bonus;
            scoreText.setText("Score: " + score);
            resultText.setText("Correct!");
        } else {
            mp = MediaPlayer.create(this, R.raw.wrong);
            bonus = -11;
            resultText.setText("Wrong...");
        }
        disablingButtons();
        if (sound) {
            mp.setVolume(1, 1);
        } else
            mp.setVolume(0, 0);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                countDownTimer.onFinish();
                enablingButtons();
            }
        });

    }

    public void disablingButtons() {
        correctButton.setOnClickListener(null);
        wrongButton.setOnClickListener(null);
    }

    public void enablingButtons() {
        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIfTrue(view);
            }
        });
        wrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIfFalse(view);
            }
        });
    }

    public void endOfGame() {
        resultText.setText("Score: " + score);
        scoreText.setText("");
        countDownTimer.cancel();
        countDownTimer = null;

        correctButton.setText("Restart");
        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        wrongButton.setText("Quit");
        wrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public String makeRandomColor() {
        String color = "#";
        for (int i = 0; i < 6; i++) {
            String s = "" + new Random().nextInt(10);
            if (new Random().nextBoolean()) color += s;
            else {
                int j = (new Random().nextInt(5) + 65);
                char z = (char) j;
                color += String.valueOf(z);
            }
        }
        return color;
    }

    public void sound(View v) {
        sound = !sound;
    }
}
