package com.example.oussama.myapp;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    AbsoluteLayout myLayout;
    TextView title;
    TextView question;
    TextView resultText;
    TextView scoreText;
    TextView clockText;
    Button correctButton;
    Button wrongButton;
    MediaPlayer mp;
    float result;
    int score;
    int bonus;
    boolean correctAnswer;
    boolean next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLayout = (AbsoluteLayout) findViewById(R.id.myLayout);
        title = (TextView) findViewById(R.id.title);
        question = (TextView) findViewById(R.id.question);
        resultText = (TextView) findViewById(R.id.resultText);
        scoreText = (TextView) findViewById(R.id.score);
        clockText = (TextView) findViewById(R.id.clockText);
        correctButton = (Button) findViewById(R.id.correctButton);
        wrongButton = (Button) findViewById(R.id.wrongButton);
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                int min = (int) (l / 1000) / 60;
                int sec = (int) (l / 1000) % 60;
                clockText.setText(String.format("%02d:%02d", min, sec));
            }

            @Override
            public void onFinish() {
                endOfGame();
            }
        }.start();
        next = false;
        score = 0;
        bonus = -11;
        generateQuestion();
    }

    public void generateQuestion() {
        String color = makeRandomColor();
        myLayout.setBackgroundColor(Color.parseColor(color));
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
        question.setText(s);
        if (!correctAnswer) {
            int difference = new Random().nextInt(10);
            boolean negative = new Random().nextBoolean();
            if (negative) result -= difference;
            else result += difference;
        }
        if (operation == 3) resultText.setText(String.format("%.1f", result));
        else resultText.setText("" + (int) result);
    }

    public void checkIfTrue(View v) {

        if (correctAnswer) {
            mp = MediaPlayer.create(this, R.raw.right);
            bonus += 11;
            score += 100 + bonus;
            scoreText.setText("Score: " + score);
            resultText.setText("Skrrra !");
        } else {
            mp = MediaPlayer.create(this, R.raw.wrong);
            bonus = -11;
            resultText.setText("Math's not Hot !");
        }
        disablingButtons();
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                generateQuestion();
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
            resultText.setText("Skrrra");
        } else {
            mp = MediaPlayer.create(this, R.raw.wrong);
            bonus = -11;
            resultText.setText("Math's not Hot !");
        }
        disablingButtons();
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                generateQuestion();
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
        title.setText("BOOM Game Over !\n Score: " + score);
        scoreText.setText("");
        resultText.setText("");
        question.setText("");
        clockText.setText("");
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

}
