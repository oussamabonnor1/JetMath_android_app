package com.example.oussama.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView question;
    TextView resultText;
    TextView scoreText;
    Button correctButton;
    Button wrongButton;
    Chronometer chronometer;
    float result;
    int score;
    int bonus;
    boolean correctAnswer;
    boolean next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        question = (TextView) findViewById(R.id.question);
        resultText = (TextView) findViewById(R.id.resultText);
        scoreText = (TextView) findViewById(R.id.score);
        correctButton = (Button) findViewById(R.id.correctButton);
        wrongButton = (Button) findViewById(R.id.wrongButton);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        next = false;
        score = 0;
        bonus = -11;
        generateQuestion();
        chronometer.start();
    }

    public void generateQuestion() {
        int firstNum = new Random().nextInt(100);
        int secondNum = new Random().nextInt(100);
        int operation = new Random().nextInt(4);
        correctAnswer = new Random().nextBoolean();

        String s = "Start !";
        switch (operation) {
            case 0:
                result = firstNum + secondNum;
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
            boolean negatif = new Random().nextBoolean();
            if (negatif) result -= difference;
            else result += difference;
        }
        resultText.setText("" + result);
    }

    public void checkIfTrue(View v) {
        if (!next) {
            if (correctAnswer) {
                bonus += 11;
                score += 100 + bonus;
                scoreText.setText("Score: " + score);
                resultText.setText("Yes !");
            } else {
                bonus = -11;
                resultText.setText("Man's not Hot !");
            }
            correctButton.setText("Next one");
        } else {
            correctButton.setText("Correct");
            generateQuestion();
        }
        next = !next;
    }

    public void checkIfFalse(View v) {
        if (!next) {
            if (!correctAnswer) {
                bonus += 11;
                score += 100 + bonus;
                scoreText.setText("Score: " + score);
                resultText.setText("Yes !");
            } else {
                bonus = -11;
                resultText.setText("Man's not Hot !");
            }
            wrongButton.setText("Next one");
        } else {
            wrongButton.setText("Wrong");
            generateQuestion();
        }
        next = !next;
    }

}
